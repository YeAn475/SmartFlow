package com.springboot.smartflow.domain.member.controller;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.springboot.smartflow.domain.member.dto.AuthRequest;
import com.springboot.smartflow.domain.member.dto.AuthResponse;
import com.springboot.smartflow.domain.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "인증 API", description = "회원가입 및 로그인 관리")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "일반 회원가입", description = "이메일, 비밀번호, 이름을 입력하여 가입합니다.")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest.SignUp request) {
        authService.signUp(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인하여 Access/Refresh 토큰을 발급받습니다.")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest.Login request) {
        // 모든 로직은 서비스에서 처리하고 컨트롤러는 결과만 반환합니다.
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 이용하여 새로운 액세스 토큰을 발급합니다.")
    public ResponseEntity<AuthResponse> refresh(@ParameterObject AuthRequest.RefreshTokenRequest request) {
        AuthResponse tokens = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "서버에서 해당 리프레시 토큰을 삭제합니다.")
    public ResponseEntity<String> logout(@ParameterObject AuthRequest.RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}