package com.springboot.smartflow.domain.alarm.service;

import com.springboot.smartflow.domain.alarm.dto.AlarmResponseDTO;
import com.springboot.smartflow.enums.AlarmType;
import java.util.List;

public interface AlarmService {

    void sendAlarm(Long receiverId, String title, String content, AlarmType type);

    List<AlarmResponseDTO> getAlarmList(Long userId);

    int getUnreadCount(Long userId);

    void markAsRead(Long alarmId);

    void markAllAsRead(Long userId);

    void deleteAlarm(Long alarmId);
}