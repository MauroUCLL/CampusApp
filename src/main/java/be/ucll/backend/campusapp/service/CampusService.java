package be.ucll.backend.campusapp.service;

import be.ucll.backend.campusapp.exception.CampusException;
import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.model.Lokaal;
import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        return campusRepository.findCampusByName(id)
                .orElseThrow(() -> new CampusException("Campus not found"));
    }

    public Campus createCampus(Campus campus) {
        Optional<Campus> existingCampus = campusRepository.findCampusByName(campus.getName());
        if (existingCampus.isPresent()) {
            throw new CampusException("Campus already exists: " + campus.getName());
        }
        List<Lokaal> lokalen = campus.getLokalen();
        if (lokalen != null) {
            Set<String> names = new HashSet<>();
            for (Lokaal lokaal : lokalen) {
                if (!names.add(lokaal.getName())) {
                    // add() returns false if the name is already in the set
                    throw new CampusException(
                            "Duplicate room name in campus: " + lokaal.getName()
                    );
                }
            }
        }
        return campusRepository.save(campus);
    }

    public List<Lokaal> findAllLokalenWithDynamicFilters(String id,
                                                         LocalDate availableFrom,
                                                         LocalDate availableUntil,
                                                         Integer minNumberOfSeats) {

        Campus campus = campusRepository.findCampusByName(id)
                .orElseThrow(() -> new CampusException("Campus " + id + " not found"));

        List<Lokaal> allRooms = campus.getLokalen();

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

    public Optional<Lokaal> findLokaalById(String campusId, Long roomId) {
        return findById(campusId)
                .getLokalen()
                .stream()
                .filter(l -> l.getId().equals(roomId))
                .findFirst();

    }

    public List<Reservatie> findReservatiesByRoomId(String campusId, long roomId) {
        Lokaal lokaal = findLokaalById(campusId, roomId)
                .orElseThrow(() -> new CampusException(
                        "lokaal with id " + roomId + " not found"
                ));

        if (lokaal.getReservaties() != null) return lokaal.getReservaties();
        return List.of();
    }
}
