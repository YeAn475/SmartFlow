package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.LeaveStatus;
import com.springboot.smartflow.enums.LeaveType;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LeaveRequest {
    private Long requestId;      // request_id
    private Long requesterId;    // requester_id
    private Long approverId;     // approver_id
    private LeaveType leaveType; // AL, AM, PM
    private String reason;
    private LeaveStatus status;  // PENDING, APPROVED, REJECTED
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}