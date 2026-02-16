package com.springboot.smartflow.domain.member.service;

import com.springboot.smartflow.domain.member.dto.AuthRequest;
import com.springboot.smartflow.domain.member.dto.AuthResponse;

public interface AuthService {
    /**
     * 일반 회원가입 처리
     */
    void signUp(AuthRequest.SignUp request);

    /**
     * 로그인 처리 후 토큰 정보 반환
     */
    AuthResponse login(AuthRequest.Login request);
}