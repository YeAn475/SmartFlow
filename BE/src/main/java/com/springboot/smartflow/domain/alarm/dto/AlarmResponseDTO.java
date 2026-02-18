package com.springboot.smartflow.domain.alarm.dto;

import com.springboot.smartflow.enums.AlarmType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class AlarmResponseDTO {
    private Long alarmId;
    private String title;
    private String content;
    private AlarmType type;
    private boolean isRead;
    private LocalDateTime createdAt;
}