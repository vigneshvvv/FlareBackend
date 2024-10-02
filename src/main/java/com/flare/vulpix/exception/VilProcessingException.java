package com.flare.vulpix.exception;

public class VilProcessingException extends Exception {

    public VilProcessingException(String message, String errorCode){
        super(errorCode + "::" + message);
    }
}
