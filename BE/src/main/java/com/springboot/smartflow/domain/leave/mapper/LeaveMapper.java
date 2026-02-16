package com.springboot.smartflow.domain.leave.mapper;

import com.springboot.smartflow.entity.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveMapper {
    // 연차 신청 저장
    void insertLeaveRequest(LeaveRequest leaveRequest);

    // 내 신청 이력 조회
    List<LeaveRequest> findByRequesterId(Long requesterId);

    // 결재 대기 목록 조회 (USER_ADMIN용)
    List<LeaveRequest> findPendingRequestsByApproverId(Long approverId);

    // 상태 변경 (승인/반려)
    void updateStatus(Long requestId, String status);
}
