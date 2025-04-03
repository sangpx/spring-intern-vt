package com.demo.project_intern.service;

import com.demo.project_intern.dto.request.user.AuthenticationRequest;
import com.demo.project_intern.dto.request.user.IntrospectRequest;
import com.demo.project_intern.dto.request.user.LogoutRequest;
import com.demo.project_intern.dto.request.user.RefreshRequest;
import com.demo.project_intern.dto.response.AuthenticationResponse;
import com.demo.project_intern.dto.response.IntrospectResponse;
import com.nimbusds.jose.*;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}