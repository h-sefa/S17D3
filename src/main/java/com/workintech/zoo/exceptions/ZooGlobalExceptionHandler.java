package com.workintech.zoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> zooExceptionHandler(ZooException zooException) {

        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(zooException.getMessage(), zooException.getHttpStatus().value(), System.currentTimeMillis());
        System.out.println("zooErrorResponse : " + zooErrorResponse);
        return new ResponseEntity<>(zooErrorResponse, zooException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> exceptionHandler(Exception exception){
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse( exception.getMessage(), HttpStatus.BAD_REQUEST.value(),System.currentTimeMillis());
        return new ResponseEntity<>(zooErrorResponse,HttpStatus.BAD_REQUEST);
    }
}
