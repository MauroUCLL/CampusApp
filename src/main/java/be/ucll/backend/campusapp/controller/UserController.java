package be.ucll.backend.campusapp.controller;

import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.model.User;
import be.ucll.backend.campusapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers(@RequestParam(required = false) String nameMatches) {
        return userService.getUsers(nameMatches);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}/reservaties/{reservatieId}")
    public User addReservatie(
            @PathVariable long userId,
            @PathVariable long reservatieId) {
        return userService.addReservatie(userId, reservatieId);
    }

    @GetMapping("/{userId}/reservaties/{reservatieId}")
    public List<Reservatie> getReservatiesById(@PathVariable long userId, @PathVariable long reservatieId) {
        return userService.getReservatiesForUserById(userId, reservatieId);
    }

    @GetMapping("/{id}/reservaties")
    public List<Reservatie> getReservaties(@PathVariable long id) {
        return userService.getReservatiesForUser(id);
    }

    @PutMapping("/{userId}/reservaties/{reservatieId}/rooms/{roomId}")
    public Reservatie addRoom(@PathVariable long userId, @PathVariable long reservatieId, @PathVariable long roomId) {
        return userService.addRoom(userId, reservatieId, roomId);
    }
}