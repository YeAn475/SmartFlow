package com.springboot.smartflow.entity;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MessageReadReceipt {
    private Long messageId;
    private Long userId;
    private Boolean isRead;
}