package com.dashboard.dashboard.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dashboard.dashboard.exceptions.AccountNotFoundException;
import com.dashboard.dashboard.exceptions.GoalNotFoundException;
import com.dashboard.dashboard.exceptions.InvalidPathVariableException;
import com.dashboard.dashboard.exceptions.TransactionNotFoundException;
import com.dashboard.dashboard.exceptions.UserAlreadyLogged;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    private ResponseEntity<String> transactionNotFoundHandler(TransactionNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<String> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception) {
        String errorResponse = "Failed to convert '" + exception.getName() + "' with value: '" + exception.getValue()
                + "'";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    private ResponseEntity<String> accountNotFoundHandler(AccountNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
    }

    @ExceptionHandler(GoalNotFoundException.class)
    private ResponseEntity<String> GoalNotFoundException(GoalNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal not found.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {

        return ResponseEntity.status(400).body(exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException exception) {

        return ResponseEntity.status(500).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidPathVariableException.class)
    public ResponseEntity<String> handleInvalidPathVariableException(InvalidPathVariableException exception) {

        return ResponseEntity.status(500).body(exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyLogged.class)
    private ResponseEntity<String> handleUserAlreadyLogged(UserAlreadyLogged exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User already logged");
    }

}
