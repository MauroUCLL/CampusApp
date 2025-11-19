package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.model.Lokaal;
import be.ucll.backend.campusapp.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

                    // Seats filter
                    if (minNumberOfSeats != null && lokaal.getAantalPersonen() < minNumberOfSeats) {
                        return false;
                    }

                    // Availability filter
                    if (availableFrom != null || availableUntil != null) {
                        return lokaal.getReservaties().stream().allMatch(res -> {

                            LocalDate resStart = res.getStartDate();
                            LocalDate resEnd = res.getEndDate();

                            LocalDate from = (availableFrom != null) ? availableFrom : LocalDate.MIN;
                            LocalDate until = (availableUntil != null) ? availableUntil : LocalDate.MAX;

                            return resEnd.isBefore(from) || resStart.isAfter(until);  // return true → room NOT available → excluded
                        });
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public Optional<Lokaal> findLokaalById(String campusId, Long lokaalId) {
        return findById(campusId)
                .getLokalen()
                .stream()
                .filter(l -> l.getId().equals(lokaalId))
                .findFirst();
    }
}
