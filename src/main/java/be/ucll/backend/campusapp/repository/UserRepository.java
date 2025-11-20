package be.ucll.backend.campusapp.repository;

import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT r FROM User u JOIN u.reservaties r WHERE u.id = :userId")
    List<Reservatie> findReservatiesForUser(@Param("userId") long userId);

    boolean existsUserByMail(String mail);
}
