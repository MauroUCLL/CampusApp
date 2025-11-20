package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.exception.EmailAlreadyUsedException;
import be.ucll.backend.campusapp.exception.ReservatieException;
import be.ucll.backend.campusapp.exception.UserException;
import be.ucll.backend.campusapp.model.Lokaal;
import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.model.User;
import be.ucll.backend.campusapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(String nameMatches) {
        if (nameMatches == null || nameMatches.isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.findByNaamContaining(nameMatches);
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

    public List<Reservatie> getReservatiesForUserById(long userId, long reservatieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found userid:" + userId));

        return user.getReservaties().stream()
                .filter(r -> r.getId() == reservatieId)
                .collect(Collectors.toList());
    }

    public Reservatie addRoom(long userId, long reservatieId, long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found userid:" + userId));
        Reservatie reservatie = user.getReservaties().stream()
                .filter(r -> r.getId() == reservatieId)
                .findFirst()
                .orElseThrow(() -> new ReservatieException("Reservatie not found with id:" + reservatieId));
        Lokaal lokaal = reservatie.getLokalen().stream()
                .filter(room -> room.getId() == roomId)
                .findFirst()
                .orElseThrow(() -> new ReservatieException("Room not found with id:" + roomId));

        if (!reservatie.getLokalen().contains(lokaal)) {
            reservatie.getLokalen().add(lokaal);
        }

        userRepository.save(user);

        return reservatie;
    }
}
