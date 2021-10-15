package com.shuoye.video.utils.network.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends Exception {
    private int code;
    private String displayMessage;

    /**
     * @deprecated
     */
    public ApiException(int code, String displayMessage) {
        super(displayMessage);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.displayMessage = message;
    }
}

