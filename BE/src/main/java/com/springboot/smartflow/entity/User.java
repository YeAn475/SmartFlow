package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.UserRole;
import com.springboot.smartflow.enums.UserStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
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
    private String providerId;
    private Boolean isDeleted;
    private UserStatus userStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}