package be.ucll.backend.campusapp.repository;

import be.ucll.backend.campusapp.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Campus findCampusByName(String name);
}
