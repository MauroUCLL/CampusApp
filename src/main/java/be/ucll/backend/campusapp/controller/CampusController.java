package be.ucll.backend.campusapp.controller;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.service.CampusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {
    private final CampusService campusService;

    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public List<Campus> findAll() {
        return campusService.findAll();
    }

    @GetMapping("/{id}")
    public Campus findById(@PathVariable String id) {
        return campusService.findById(id);
    }

    @PostMapping
    public Campus createCampus(@RequestBody Campus campus) {
        return campusService.createCampus(campus);
    }
}
