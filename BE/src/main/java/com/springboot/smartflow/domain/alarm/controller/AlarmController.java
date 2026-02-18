package com.springboot.smartflow.domain.alarm.controller;

import com.springboot.smartflow.domain.alarm.dto.AlarmResponseDTO;
import com.springboot.smartflow.domain.alarm.service.AlarmService;
import com.springboot.smartflow.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alarm")
@Tag(name = "알람 API", description = "알람 관련 api")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "나의 알람 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<List<AlarmResponseDTO>> getMyAlarms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(alarmService.getAlarmList(userDetails.getUserId()));
    }

    @Operation(summary = "안 읽은 알람 개수")
    @GetMapping("/unread-count")
    public ResponseEntity<Integer> getUnreadCount(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(alarmService.getUnreadCount(userDetails.getUserId()));
    }

    @Operation(summary = "알람 읽음 처리")
    @PatchMapping("/{alarmId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long alarmId) {
        alarmService.markAsRead(alarmId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 알람 읽음 처리")
    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        alarmService.markAllAsRead(userDetails.getUserId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알람 삭제")
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long alarmId) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity.ok().build();
    }
}