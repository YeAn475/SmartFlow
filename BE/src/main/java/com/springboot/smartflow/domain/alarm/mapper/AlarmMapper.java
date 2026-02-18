package com.springboot.smartflow.domain.alarm.mapper;

import com.springboot.smartflow.domain.alarm.dto.AlarmResponseDTO;
import com.springboot.smartflow.enums.AlarmType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AlarmMapper {
    // 알람 생성 (내부 서비스용)
    void insertAlarm(@Param("receiverId") Long receiverId, 
                     @Param("title") String title, 
                     @Param("content") String content, 
                     @Param("type") AlarmType type);

    // 나의 알람 목록 조회
    List<AlarmResponseDTO> findAllByUserId(Long userId);

    // 안 읽은 알람 개수
    int countUnread(Long userId);

    // 특정 알람 읽음 처리
    void updateReadStatus(Long alarmId);

    // 모든 알람 읽음 처리
    void updateAllReadStatus(Long userId);

    // 알람 삭제
    void deleteById(Long alarmId);
}