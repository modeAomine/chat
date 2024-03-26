package com.example.demo.Exception;

public class CommentSavingException extends Throwable {
    public CommentSavingException(String errorSavingComment, Exception e) {
        super(errorSavingComment, e);
    }
}
