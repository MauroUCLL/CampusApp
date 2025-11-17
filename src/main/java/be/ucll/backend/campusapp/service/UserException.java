package be.ucll.backend.campusapp.service;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
    public UserException(){}
}
