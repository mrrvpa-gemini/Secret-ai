package com.darklord.ai.data.models;
public class ChatMessage {
    private String sender;
    private String message;
    private boolean isAI;
    private long timestamp;
    public ChatMessage(String sender, String message, boolean isAI, long timestamp) {
        this.sender = sender; this.message = message; this.isAI = isAI; this.timestamp = timestamp;
    }
    public String getSender() { return sender; }
    public String getMessage() { return message; }
    public boolean isAI() { return isAI; }
    public long getTimestamp() { return timestamp; }
}
