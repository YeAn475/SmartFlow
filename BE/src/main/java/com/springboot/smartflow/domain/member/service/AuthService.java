package com.springboot.smartflow.domain.member.service;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.springboot.smartflow.domain.member.dto.AuthRequest;
import com.springboot.smartflow.domain.member.dto.AuthResponse;

public interface AuthService {

    void signUp(AuthRequest.SignUp request);

    AuthResponse login(AuthRequest.Login request);

    void logout(String refreshToken);

    AuthResponse refresh(String refreshToken);
}