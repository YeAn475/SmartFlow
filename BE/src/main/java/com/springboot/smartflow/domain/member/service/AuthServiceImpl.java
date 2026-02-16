package com.springboot.smartflow.domain.member.service;

import com.springboot.smartflow.domain.member.dto.AuthRequest;
import com.springboot.smartflow.domain.member.dto.AuthResponse;
import com.springboot.smartflow.domain.member.mapper.MemberMapper;
import com.springboot.smartflow.entity.User;
import com.springboot.smartflow.enums.UserRole;
import com.springboot.smartflow.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signUp(AuthRequest.SignUp request) {
        // 1. 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 2. 중복 이메일 체크
        memberMapper.findByEmail(request.getEmail())
                .ifPresent(u -> { throw new RuntimeException("이미 존재하는 이메일입니다."); });

        // 3. User 엔티티 빌드 (departmentId는 제외하거나 기본값 처리)
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(UserRole.USER)
                .totalLeave(15F)  // 기본 연차 부여
                .remainLeave(15F)
                .build();

        memberMapper.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest.Login request) {
        // 1. 유저 존재 여부 확인
        User user = memberMapper.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        // 2. 비밀번호 일치 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        // 3. JWT 토큰 발행 (핵심 비즈니스 로직)
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        // 4. 응답 DTO 조립
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}