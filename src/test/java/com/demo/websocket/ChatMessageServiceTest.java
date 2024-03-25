package com.demo.websocket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.demo.websocket.chat.ChatMessage;
import com.demo.websocket.rest.ChatMessageRepository;
import com.demo.websocket.rest.ChatMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {

    @Mock
    private ChatMessageRepository messageRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private ChatMessageService chatService;

    @Test
    public void testAddMessage() {
        // Create a sample message
        ChatMessage message = new ChatMessage("Test Message", "User1");

        // Mock the behavior of messageRepository.save()
        when(messageRepository.save(message)).thenReturn(message);

        // Call the method to be tested
        ChatMessage savedMessage = chatService.addMessage(message);

        // Verify that messageRepository.save() was called
        verify(messageRepository, times(1)).save(message);

        // Verify that the returned message is the same as the original message
        assertEquals("Test Message", savedMessage.getContent());
        assertEquals("User1", savedMessage.getSender());
    }

    @Test
    public void testDeleteMessage() {
        // Create a sample message ID
        Long messageId = 1L;

        // Mock the behavior of messageRepository.findById()
        ChatMessage message = new ChatMessage("Test Message", "User1");
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        // Call the method to be tested
        boolean result = chatService.deleteMessage(messageId);

        // Verify that messageRepository.findById() was called
        verify(messageRepository, times(1)).findById(messageId);

        // Verify that messageRepository.deleteById() was called
        verify(messageRepository, times(1)).deleteById(messageId);

        // Verify that the result is true (message deleted)
        assertTrue(result);
    }

    @Test
    public void testDeleteMessageNotFound() {
        // Create a sample message ID
        Long messageId = 1L;

        // Mock the behavior of messageRepository.findById() for a non-existent message
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        // Call the method to be tested
        boolean result = chatService.deleteMessage(messageId);

        // Verify that messageRepository.findById() was called
        verify(messageRepository, times(1)).findById(messageId);

        // Verify that messageRepository.deleteById() was not called
        verify(messageRepository, never()).deleteById(messageId);

        // Verify that the result is false (message not found)
        assertFalse(result);
    }

    @Test
    public void testGetChatHistory() {
        // Create sample chat messages
        ChatMessage message1 = new ChatMessage("Message 1", "User1");
        ChatMessage message2 = new ChatMessage("Message 2", "User2");
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        // Mock the behavior of messageRepository.findAll()
        when(messageRepository.findAll()).thenReturn(messages);

        // Call the method to be tested
        List<ChatMessage> result = chatService.getAllMessages();

        // Verify that messageRepository.findAll() was called
        verify(messageRepository, times(1)).findAll();

        // Verify that the result contains the expected messages
        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getContent());
        assertEquals("User2", result.get(1).getSender());
    }
}
