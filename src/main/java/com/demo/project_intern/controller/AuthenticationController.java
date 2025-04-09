package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.request.user.AuthenticationRequest;
import com.demo.project_intern.dto.request.user.IntrospectRequest;
import com.demo.project_intern.dto.request.user.LogoutRequest;
import com.demo.project_intern.dto.request.user.RefreshRequest;
import com.demo.project_intern.dto.response.AuthenticationResponse;
import com.demo.project_intern.dto.response.IntrospectResponse;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("${api.base-path}/auth")
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(method = "POST", summary = "Login", description = "API Login")
    ResponseData<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseData.<AuthenticationResponse>builder()
                .message(Translator.getSuccessMessage("Authentication", EntityType.AUTHENTICATION))
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    @Operation(method = "POST", summary = "Introspect", description = "API Introspect")
    ResponseData<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return ResponseData.<IntrospectResponse>builder()
                .message(Translator.getSuccessMessage("Introspect", EntityType.INTROSPECT))
                .data(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/refresh")
    @Operation(method = "POST", summary = "Refresh", description = "API Refresh")
    ResponseData<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        return ResponseData.<AuthenticationResponse>builder()
                .message(Translator.getSuccessMessage("Refresh", EntityType.REFRESH))
                .data(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    @Operation(method = "POST", summary = "Logout", description = "API Logout")
    ResponseData<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseData.<Void>builder().build();
    }
}