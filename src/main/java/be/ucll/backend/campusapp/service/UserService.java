package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.model.User;
import be.ucll.backend.campusapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found userid:" + id));
    }

    public User createUser(@RequestBody User user) {

        if (userRepository.existsUserByMail(user.getMail())) {
            throw new EmailAlreadyUsedException("Email already exists: " + user.getMail());
        }

        return userRepository.save(user);
    }

    public User addReservatie(long userId, long reservatieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found userid:" + userId));
        List<Reservatie> reservaties = userRepository.findReservatiesForUser(reservatieId);

        user.setReservaties(reservaties);
        return userRepository.save(user);
    }

    public List<Reservatie> getReservatiesForUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserException::new)
                .getReservaties();
    }
}
