package com.demo.websocket.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.demo.websocket.chat.ChatMessage;
import java.time.LocalDateTime;

@Service
public class ChatMessageService {
	
//	private SimpMessagingTemplate messagingTemplate;
    private ChatMessageRepository messageRepository;

    @Autowired
    public ChatMessageService(SimpMessagingTemplate messagingTemplate, ChatMessageRepository messageRepository) {
//        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
    }

    public boolean deleteMessage(Long messageId) {
        Optional<ChatMessage> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public ChatMessage addMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
//        messagingTemplate.convertAndSend("/topic/public", message);
		return message;
    }
//    @Transactional(readOnly = true)
//    public List<ChatMessage> getAllMessages() {
//        return messageRepository.findAll();
//    }

    public List<ChatMessage> getAllMessages() {
        return messageRepository.findAll();
    }
    // Other service methods as needed
}


