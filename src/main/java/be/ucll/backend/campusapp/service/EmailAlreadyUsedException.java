package be.ucll.backend.campusapp.service;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String message){
        super(message);
    }
}
