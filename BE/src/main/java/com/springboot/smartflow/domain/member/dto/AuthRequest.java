package com.springboot.smartflow.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class AuthRequest {

    @Getter @Setter
    @Schema(description = "회원가입 요청 객체")
    public static class SignUp {
        @Schema(example = "user@example.com")
        private String email;

        @Schema(example = "password123!")
        private String password;

        @Schema(example = "password123!")
        private String passwordConfirm;

        @Schema(example = "홍길동")
        private String name;
    }

    @Getter @Setter
    @Schema(description = "로그인 요청 객체")
    public static class Login {
        @Schema(example = "user@example.com")
        private String email;
        @Schema(example = "password123!")
        private String password;
    }
}