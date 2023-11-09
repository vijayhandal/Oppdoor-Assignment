package com.vijay.travel.web.exhandler;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vijay.travel.exception.HttpMethodNotImplementedException;
import com.vijay.travel.exception.IllegalRequestException;
import com.vijay.travel.exception.ResourceAbsentException;
import com.vijay.travel.exception.ResourceExistException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceAbsentException.class)
    protected ProblemDetail handleResourceAbsentException(ResourceAbsentException e) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("resource-not-found"));
        problemDetail.setTitle(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(ResourceExistException.class)
    protected ProblemDetail handleResourceExistException(ResourceExistException e) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setType(URI.create("resource-already-exists"));
        problemDetail.setTitle(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(IllegalRequestException.class)
    protected ProblemDetail handleIllegalRequestException(IllegalRequestException e) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("invalid-request"));
        problemDetail.setTitle(e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(HttpMethodNotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    protected void handleHttpMethodNotImplementedException(HttpMethodNotImplementedException e) {
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected void handleException(Exception e) {
    }
}
