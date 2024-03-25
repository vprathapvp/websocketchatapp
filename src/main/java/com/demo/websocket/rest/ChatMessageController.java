package com.demo.websocket.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.websocket.chat.ChatMessage;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ChatMessageController {

    private final ChatMessageService messageService;

    @Autowired
    public ChatMessageController(ChatMessageService messageService) {
        this.messageService = messageService;
    }

 // Endpoint to delete a message by its ID
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        if (messageService.deleteMessage(messageId)) {
            return ResponseEntity.ok().body("Message with ID " + messageId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with ID " + messageId + " not found.");
        }
    }
    @GetMapping
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        List<ChatMessage> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Other endpoints such as delete, update, etc.
}

