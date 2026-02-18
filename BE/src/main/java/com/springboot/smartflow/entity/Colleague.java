package com.springboot.smartflow.entity;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Colleague {
    private Long userId;
    private Long friendId;
    private LocalDateTime createdAt;
}