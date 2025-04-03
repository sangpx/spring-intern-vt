package com.demo.project_intern.config;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.request.user.IntrospectRequest;
import com.demo.project_intern.dto.response.IntrospectResponse;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@Repository
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private final AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    //để jwt decoder nhận biết token đó đã bị invalid >> CustomJwtDecoder
    public Jwt decode(String token) throws JwtException {
        try {
            IntrospectResponse introspectResponse = authenticationService.introspect(IntrospectRequest
                    .builder()
                    .token(token)
                    .build());
            if (!introspectResponse.isValid())
                throw new BaseLibraryException(ErrorCode.INVALID_TOKEN);
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}