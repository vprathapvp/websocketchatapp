package com.demo.websocket.chat;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.demo.websocket.rest.ChatMessageService;

@Controller
public class ChatController {

	String HARD_CODED_PASSWORD = "password";
	String[] HARD_CODED_USERNAME = { "prathap", "velsamy", "demo" };
	private final ChatMessageService messageService;

	@Autowired
	public ChatController(ChatMessageService messageService) {
		this.messageService = messageService;
	}

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		if (!isValidUser(chatMessage)) {
			// Handle invalid credentials (e.g., send an error message)
			ChatMessage errorMessage = new ChatMessage();
			errorMessage.setContent("Invalid username or password");
			return errorMessage;
		}
		messageService.addMessage(chatMessage);
		return chatMessage;
	}
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Validate user credentials before adding
		if (!isValidUser(chatMessage)) {
			// Handle invalid credentials (e.g., send an error message)
			ChatMessage errorMessage = new ChatMessage();
			errorMessage.setContent("Invalid username or password");
			return errorMessage;
		}

		// Add username in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}

	// Method to validate user credentials
	private boolean isValidUser(ChatMessage chatMessage) {
		String sender = chatMessage.getSender();
//        String password = chatMessage.getContent(); // Assuming password is sent in content field

		// Compare with hardcoded credentials
		return Arrays.asList(HARD_CODED_USERNAME).contains(sender);
//		&&Arrays.asList(HARD_CODED_PASSWORD).contains(password);
	}
}
