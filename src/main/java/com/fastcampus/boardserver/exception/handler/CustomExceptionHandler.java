package com.fastcampus.boardserver.exception.handler;

import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * RuntimeException 예외 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "RuntimeException", e.getMessage(), e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    /**
     * BoardServerException 예외 처리
     */
    @ExceptionHandler(BoardServerException.class)
    public ResponseEntity<Object> handleBoardServerException(BoardServerException e) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "BoardServerException", e.getMessage(), e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    /**
     * 에외의 최상위 부모인 Exception 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "Exception", e.getMessage(), e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

}
