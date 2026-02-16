package com.springboot.smartflow.domain.member.controller;

import com.springboot.smartflow.domain.member.dto.AuthRequest;
import com.springboot.smartflow.domain.member.dto.AuthResponse;
import com.springboot.smartflow.domain.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "회원가입 및 로그인 관리")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "일반 회원가입", description = "이메일, 비밀번호, 이름을 입력하여 가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest.SignUp request) {
        authService.signUp(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인하여 Access/Refresh 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest.Login request) {
        // 모든 로직은 서비스에서 처리하고 컨트롤러는 결과만 반환합니다.
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}