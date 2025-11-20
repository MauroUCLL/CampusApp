package be.ucll.backend.campusapp.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
    public UserException(){}
}
