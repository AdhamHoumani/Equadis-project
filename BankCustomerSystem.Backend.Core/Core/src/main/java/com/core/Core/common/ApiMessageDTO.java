package com.core.Core.common;

public class ApiMessageDTO {
    private String Message;
    private int MessageType;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }

    public ApiMessageDTO(int messageType, String message) {
        Message = message;
        MessageType = messageType;
    }
}
