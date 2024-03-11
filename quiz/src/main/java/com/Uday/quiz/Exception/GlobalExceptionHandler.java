package com.Uday.quiz.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException() {
        HashMap<String, String> error= new HashMap<>();
        error.put("Error: ", "DuplicateEmailException");
        error.put("Description: ", "The email that you have entered is already used!");
        error.put("Status: ", String.valueOf(HttpStatus.UNAUTHORIZED));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StudentAuthenticationException.class)
    public ResponseEntity<Object> handleStudentAuthenticationException() {
        HashMap<String, String> error= new HashMap<>();
        error.put("Error: ", "StudentAuthenticationException");
        error.put("Description: ", "The credentials are not matching to the details you've provided");
        error.put("Status: ", String.valueOf(HttpStatus.UNAUTHORIZED));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(QuizSubmissionException.class)
    public ResponseEntity<Object> handleQuizSubmissionException() {
        HashMap<String, String> error= new HashMap<>();
        error.put("Error: ", "QuizSubmissionException");
        error.put("Description: ", "The student not found for the provided ID");
        error.put("Status: ", String.valueOf(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException() {
        HashMap<String, String> error= new HashMap<>();
        error.put("Error: ", "Exception");
        error.put("Description: ", "There is an error in the server");
        error.put("Status: ", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
