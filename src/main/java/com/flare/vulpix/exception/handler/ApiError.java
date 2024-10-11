package com.flare.vulpix.exception.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ApiError {
    private String timestamp;
    private String message;
    private HttpStatus status;

    public ApiError(){
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ApiError(HttpStatus status){
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex){
        this();
        this.status = status;
        this.message = ex.getMessage();
    }
}
