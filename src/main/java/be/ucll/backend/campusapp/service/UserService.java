package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.model.User;
import be.ucll.backend.campusapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addReservatie(long userId, long reservatieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
