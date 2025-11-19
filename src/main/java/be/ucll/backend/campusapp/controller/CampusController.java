package be.ucll.backend.campusapp.controller;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.model.Lokaal;
import be.ucll.backend.campusapp.service.CampusService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campuses")
public class CampusController {
    private final CampusService campusService;

    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public List<Campus> getAll() {
        return campusService.findAll();
    }

    @GetMapping("/{id}")
    public Campus getById(@PathVariable String id) {
        return campusService.findById(id);
    }

    @PostMapping
    public Campus createCampus(@RequestBody Campus campus) {
        return campusService.createCampus(campus);
    }

    @GetMapping("/{id}/rooms")
    public List<Lokaal> getLokaalByCampus(@PathVariable String id,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableFrom,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableUntil,
                                          @RequestParam(required = false) Integer minNumberOfSeats) {
        return campusService.findAllLokalenWithDynamicFilters(id, availableFrom, availableUntil, minNumberOfSeats);
    }

    @GetMapping("/{campusId}/rooms/{roomId}")
    public Optional<Lokaal> getLokaalByCampus(@PathVariable("campusId") String campusId, @PathVariable("roomId") long roomId) {
        return campusService.findLokaalById(campusId, roomId);
    }
}
