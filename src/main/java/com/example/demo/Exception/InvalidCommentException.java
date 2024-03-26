package com.example.demo.Exception;

public class InvalidCommentException extends Throwable {
    public InvalidCommentException(String errorInvalidComment) {
        super(errorInvalidComment);
    }
}
