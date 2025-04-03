package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.request.user.AuthenticationRequest;
import com.demo.project_intern.dto.request.user.IntrospectRequest;
import com.demo.project_intern.dto.request.user.LogoutRequest;
import com.demo.project_intern.dto.request.user.RefreshRequest;
import com.demo.project_intern.dto.response.AuthenticationResponse;
import com.demo.project_intern.dto.response.IntrospectResponse;
import com.demo.project_intern.entity.InvalidatedToken;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.InvalidatedTokenRepository;
import com.demo.project_intern.repository.UserRepository;
import com.demo.project_intern.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean isValid = true;
        try {
            //verifyToken
            verifyToken(request.getToken(), false);
        } catch (BaseLibraryException e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        UserEntity user = userRepository.findByUserName(request.getUsername())
                            .orElseThrow(() -> new BaseLibraryException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new BaseLibraryException(ErrorCode.UNAUTHENTICATED);
        //generate Token
        String token = generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            SignedJWT signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken
                                                .builder()
                                                .id(jit)
                                                .expiryTime(expiryTime)
                                                .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (BaseLibraryException exception){
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        //verifyToken
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        //invalidate old token
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);
        String userName = signedJWT.getJWTClaimsSet().getSubject();
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.UNAUTHENTICATED));
        String token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    //handle generate token
    private String generateToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512); // su dung thuat toan de gen ra token

        //build payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder()
                .subject(user.getUserName())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("userId", user.getId())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); //create payload
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            //sign jwt
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize(); //serialize -> String
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    //handle verifyToken
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        //check token expiryTime?
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT // verify for refresh
                            .getJWTClaimsSet()
                            .getIssueTime()
                            .toInstant()
                            .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                            .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime(); // verify for authenticate and introspect
        boolean verified = signedJWT.verify(verifier);
        //check when logged out
        if (!(verified && expiryTime.after(new Date())))
            throw new BaseLibraryException(ErrorCode.UNAUTHENTICATED);
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new BaseLibraryException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }

    private String buildScope(UserEntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });
        return stringJoiner.toString();
    }
}