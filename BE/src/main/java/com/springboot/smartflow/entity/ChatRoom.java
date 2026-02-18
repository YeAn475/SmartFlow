package com.springboot.smartflow.entity;

import com.springboot.smartflow.enums.ChatRoomType; // 별도 생성 필요
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatRoom {
    private Long roomId;
    private String name;
    private ChatRoomType type;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}