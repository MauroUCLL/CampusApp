package be.ucll.backend.campusapp.controller;

import be.ucll.backend.campusapp.model.Campus;
import be.ucll.backend.campusapp.service.CampusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
