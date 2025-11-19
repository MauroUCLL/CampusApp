package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.model.Lokaal;
import be.ucll.backend.campusapp.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Lokaal> findAllLokalenWithDynamicFilters(String id,
                                                         LocalDate availableFrom,
                                                         LocalDate availableUntil,
                                                         Integer minNumberOfSeats) {
        List<Lokaal> allRooms = campusRepository.findCampusByName(id).getLokalen();

        return allRooms.stream()
                .filter(lokaal -> {
                    // Filter by minimum number of seats if provided
                    if (minNumberOfSeats != null && lokaal.getAantalPersonen() < minNumberOfSeats) {
                        return false;
                    }

                    // Filter by availability
                    if (availableFrom != null || availableUntil != null) {
                        return lokaal.getReservaties().stream().noneMatch(res -> {
                            // If reservation overlaps the requested period, exclude room
                            LocalDate resStart = res.getStartDate();
                            LocalDate resEnd = res.getEndDate();

                            boolean overlaps;

                            if (availableFrom != null && availableUntil != null) {
                                overlaps = !(resEnd.isBefore(availableFrom) || resStart.isAfter(availableUntil));
                            } else if (availableFrom != null) {
                                overlaps = !resEnd.isBefore(availableFrom);
                            } else {
                                overlaps = !resStart.isAfter(availableUntil);
                            }

                            return overlaps;
                        });
                    }

                    return true;
                })
                .collect(Collectors.toList());
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
