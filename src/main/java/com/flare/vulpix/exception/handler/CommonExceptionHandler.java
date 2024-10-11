package com.flare.vulpix.exception.handler;

import com.flare.vulpix.exception.MicroserviceException;
import com.flare.vulpix.exception.ServiceNotAvailableException;
import com.flare.vulpix.exception.VilProcessingException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;

@ControllerAdvice
@Log4j2
public class CommonExceptionHandler extends ExceptionHandlerExceptionResolver {

    @ExceptionHandler({MicroserviceException.class})
    public ResponseEntity<Object> handleMicroServiceException(MicroserviceException exe){
        log.error(exe.getMessage(), exe);
        return buildResponseEntityForError(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,  exe));
    }

    @ExceptionHandler({VilProcessingException.class})
    public ResponseEntity<Object> handleVilProcessingException(VilProcessingException exe){
        log.error(exe.getMessage(), exe);
        return buildResponseEntityForError(new ApiError(HttpStatus.NOT_FOUND, exe));
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<Object> handleIOException(IOException ex){
        log.error(ex.getMessage(), ex);
        return buildResponseEntityForError(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception ex){
        log.error(ex.getMessage(),ex);
        return buildResponseEntityForError(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exe){
        log.error(exe.getMessage(), exe);
        return buildResponseEntityForError(new ApiError(HttpStatus.NOT_FOUND, exe));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exe){
        log.error(exe.getMessage(), exe);
        return buildResponseEntityForError(new ApiError(HttpStatus.BAD_REQUEST, exe));
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exe){
        log.error(exe.getMessage(), exe);
        return buildResponseEntityForError(new ApiError(HttpStatus.NOT_FOUND, exe));
    }

    @ExceptionHandler({ServiceNotAvailableException.class})
    public ResponseEntity<Object> handleServiceNotAvailableException(ServiceNotAvailableException exception){
        log.error(exception.getErrorMessage(), exception.getErrorCode(), exception.getErrorSource());
        return new ResponseEntity<>(exception, exception.getErrorCode());
    }


    public ResponseEntity<Object> buildResponseEntityForError(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
