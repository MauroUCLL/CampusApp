package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.model.Lokaal;
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

    public Campus findById(String id) {
        return campusRepository.findCampusByName(id);
    }

    public Campus createCampus(Campus campus) {
        if (campus.equals(campusRepository.findCampusByName(campus.getName()))) {
            throw new CampusException("Campus already exist");
        }
        return campusRepository.save(campus);
    }

    public List<Lokaal> findAllLokalen(String id) {
        return campusRepository.findCampusByName(id).getLokalen();
    }

    public Lokaal findLokaalById(String campusId, Long lokaalid) {
        List<Lokaal> foundLokaal = findById(campusId).getLokalen();
        for (Lokaal lokaal : foundLokaal) {
            if (lokaal.getId().equals(lokaalid)) {
                return lokaal;
            }
        }
        return null;
    }
}
