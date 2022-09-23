package com.kh.campingez.chat.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatUser {
	@NonNull
	private String chatroomId;
	@NonNull
	private String userId;
	private String tradeNo;
	private long lastCheck;
	private LocalDateTime createdAt;
	private LocalDateTime deletedAt;
}
