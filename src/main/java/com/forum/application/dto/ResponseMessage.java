package com.forum.application.dto;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ResponseMessage {
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timeStamp;

    public ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
