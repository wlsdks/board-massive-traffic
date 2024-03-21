package com.fastcampus.boardserver.exception;

/**
 * 런타임시 해당 메시지를 예외로 던진다.
 */
public class DuplicateIdException extends RuntimeException {

    public DuplicateIdException(String message) {
        super(message);
    }

}
