package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.AlarmType; // 분리한 Enum 임포트
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    private Long alarmId;
    private Long receiverId;
    private Long senderId;
    private String title;
    private String content;

    private AlarmType type;

    private boolean isRead;
    private LocalDateTime createdAt;
}