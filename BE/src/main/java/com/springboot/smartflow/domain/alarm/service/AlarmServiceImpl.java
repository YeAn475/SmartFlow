package com.springboot.smartflow.domain.alarm.service;

import com.springboot.smartflow.domain.alarm.dto.AlarmResponseDTO;
import com.springboot.smartflow.domain.alarm.mapper.AlarmMapper;
import com.springboot.smartflow.enums.AlarmType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmServiceImpl implements AlarmService {

    private final AlarmMapper alarmMapper;

    @Override
    @Transactional
    public void sendAlarm(Long receiverId, String title, String content, AlarmType type) {
        alarmMapper.insertAlarm(receiverId, title, content, type);
    }

    @Override
    public List<AlarmResponseDTO> getAlarmList(Long userId) {
        return alarmMapper.findAllByUserId(userId);
    }

    @Override
    public int getUnreadCount(Long userId) {
        return alarmMapper.countUnread(userId);
    }

    @Override
    @Transactional
    public void markAsRead(Long alarmId) {
        alarmMapper.updateReadStatus(alarmId);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        alarmMapper.updateAllReadStatus(userId);
    }

    @Override
    @Transactional
    public void deleteAlarm(Long alarmId) {
        alarmMapper.deleteById(alarmId);
    }
}