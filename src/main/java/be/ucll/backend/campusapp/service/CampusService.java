package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusService {

    private final CampusRepository campusRepository;

    @Autowired
    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    public List<Campus> findAll() {
        return campusRepository.findAll();
    }
}
