package com.kh.campingez.chat.model.service;

import java.util.List;

import com.kh.campingez.chat.model.dto.ChatLog;
import com.kh.campingez.chat.model.dto.ChatUser;
import com.kh.campingez.trade.model.dto.Trade;

import lombok.NonNull;

public interface ChatService {

	ChatUser findChatUserByUserId(String userId, String chatTargetId, String chatTradeNo);

	void insertChatUsers(List<ChatUser> chatUserList);

	int insertChatLog(ChatLog chatLog);

	List<ChatLog> findChatLogByChatroomId(String chatroomId);

	List<ChatUser> findMyChat(String userId);

	int deleteChatroom(ChatUser chatUser);

	String findChatTradeNo(String chatroomId);

	ChatUser deleteCheck(String chatroomId);

	Trade selectGoTradeByNo(String tradeNo);

}
