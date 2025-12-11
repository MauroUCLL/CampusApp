package be.ucll.backend.campusapp.controller;

import be.ucll.backend.campusapp.exception.CampusException;
import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.model.Lokaal;
import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.service.CampusService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/campus")
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
                                          @RequestParam(required = false)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate availableFrom,
                                          @RequestParam(required = false)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate availableUntil,
                                          @RequestParam(required = false) Integer minNumberOfSeats) {
        return campusService.findAllLokalenWithDynamicFilters(id, availableFrom, availableUntil, minNumberOfSeats);
    }

    @GetMapping("/{campusId}/rooms/{roomId}")
    public Lokaal getLokaalByCampus(@PathVariable String campusId, @PathVariable long roomId) {
        return campusService.findLokaalById(campusId, roomId)
                .orElseThrow(() -> new CampusException(
                "lokaal with id " + roomId + " not found"
        ));
    }

    @GetMapping("/{campusId}/rooms/{roomId}/reservaties")
    public List<Reservatie> getReservatiesByRoomId(@PathVariable String campusId, @PathVariable long roomId){
        return campusService.findReservatiesByRoomId(campusId, roomId);
    }
}
