package com.springboot.smartflow.domain.member.service;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.springboot.smartflow.domain.member.dto.AuthRequest;
import com.springboot.smartflow.domain.member.dto.AuthResponse;
import com.springboot.smartflow.domain.member.mapper.MemberMapper;
import com.springboot.smartflow.entity.User;
import com.springboot.smartflow.enums.UserRole;
import com.springboot.smartflow.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

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
        User user = memberMapper.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        // 로그인 성공 시 Redis에 Refresh Token 저장 (보통 7일~14일 설정)
        redisTemplate.opsForValue().set(
                "RT:" + user.getEmail(),
                refreshToken,
                Duration.ofDays(7)
        );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        // 변수명을 jwtTokenProvider로 통일
        String email = jwtTokenProvider.getEmail(refreshToken);

        // Redis에 저장된 리프레시 토큰 삭제
        redisTemplate.delete("RT:" + email);
    }

    @Override
    public AuthResponse refresh(String refreshToken) { // 반환 타입을 AuthResponse로 맞춤
        // 1. 리프레시 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 2. Redis에 저장된 토큰과 일치하는지 확인
        String email = jwtTokenProvider.getEmail(refreshToken);
        String savedToken = redisTemplate.opsForValue().get("RT:" + email);

        if (savedToken == null || !savedToken.equals(refreshToken)) {
            throw new RuntimeException("로그인 정보가 만료되었습니다.");
        }

        // 3. 유저 정보 조회 후 새로운 토큰 쌍 발급
        User user = memberMapper.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String newAccessToken = jwtTokenProvider.createAccessToken(email, user.getRole().name());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(email);

        // 4. Redis 갱신 (Refresh Token Rotation)
        redisTemplate.opsForValue().set("RT:" + email, newRefreshToken, Duration.ofDays(7));

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}