package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.AlarmType; // 별도 생성 필요
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Alarm {
    private Long alarmId;
    private Long receiverId;
    private String title;
    private String content;
    private AlarmType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
}