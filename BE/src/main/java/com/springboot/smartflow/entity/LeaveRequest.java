package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.LeaveType;
import com.springboot.smartflow.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class LeaveRequest {
    private Long requestId;
    private Long requesterId;
    private Long approverId;
    private LeaveType leaveType;
    private LeaveStatus status;
    private LocalDate leaveDate;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
}
