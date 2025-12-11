package be.ucll.backend.campusapp.repository;

import be.ucll.backend.campusapp.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findCampusByName(String name);
}
