package com.flare.vulpix.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString(callSuper = true)
public class ServiceNotAvailableException extends  Exception{
    private HttpStatus errorCode;
    private String errorMessage;
    private String errorSource;

    public ServiceNotAvailableException(HttpStatus errorCode, String errorMessage, String errorSource, Exception exception) {
        super(exception);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorSource = errorSource;
    }

}
