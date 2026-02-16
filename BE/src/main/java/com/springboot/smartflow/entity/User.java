package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private Long departmentId;
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private Float totalLeave;
    private Float remainLeave;
    private String provider;
    private String provider_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
