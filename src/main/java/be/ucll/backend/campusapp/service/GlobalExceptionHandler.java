package be.ucll.backend.campusapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public Map<String, String> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Illegal argument", e.getMessage());
        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGenericException(Exception ex) {
        return Map.of("error", "An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(CampusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserServiceException(UserException ex) {
        return Map.of("User not found", ex.getMessage());
    }

    @ExceptionHandler(CampusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleCampusServiceException(CampusException ex) {
        return Map.of("Campus not found", ex.getMessage());
    }
}
