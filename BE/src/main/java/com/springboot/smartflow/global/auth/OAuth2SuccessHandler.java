package com.springboot.smartflow.global.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 1. 소셜 로그인 종류에 따른 이메일 추출
        String email = "";
        if (attributes.get("email") != null) { // 구글
            email = (String) attributes.get("email");
        } else if (attributes.get("kakao_account") != null) { // 카카오
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
        }

        // 2. 토큰 생성 (기본 ROLE_USER 부여)
        String accessToken = jwtTokenProvider.createAccessToken(email, "ROLE_USER");
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        // 3. 쿠키에 Access Token 저장 (JSP에서 사용하기 위함)
        // 실제 운영시는 HttpOnly 설정 권장
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(1800); // 30분
        response.addCookie(accessTokenCookie);

        // 4. 메인 화면으로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, "/main");
    }
}