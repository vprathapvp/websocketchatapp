package com.demo.websocket.chat;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
	private MessageType type;
	private String content;
	private String sender;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime timestamp;
	

	// Getters and Setters
	
	

	public MessageType getType() {
		return type;
	}
	public ChatMessage(String content, String sender) {
		super();
		this.content = content;
		this.sender = sender;
	}
	public ChatMessage() {
		
	}
	public ChatMessage(MessageType type, String content, String sender, Long id, LocalDateTime timestamp) {
		super();
		this.type = type;
		this.content = content;
		this.sender = sender;
		this.id = id;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	// Builder pattern implementation
	public static class ChatMessageBuilder {
		private MessageType type;
		private String content;
		private String sender;

		public ChatMessageBuilder() {
		}

		public ChatMessageBuilder type(MessageType type) {
			this.type = type;
			return this;
		}

		public ChatMessageBuilder content(String content) {
			this.content = content;
			return this;
		}

		public ChatMessageBuilder sender(String sender) {
			this.sender = sender;
			return this;
		}

		public ChatMessage build() {
			ChatMessage message = new ChatMessage();
			message.setType(this.type);
			message.setContent(this.content);
			message.setSender(this.sender);
			return message;
		}
	}

	// Static method to create a builder
	public static ChatMessageBuilder builder() {
		return new ChatMessageBuilder();
	}

	@Override
	public String toString() {
		return "ChatMessage{" + "type=" + type + ", content='" + content + '\'' + ", sender='" + sender + '\'' + '}';
	}

	

}
