package team.campic.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.campic.collector.service.CampingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {

    private final CampingService campingService;

    @PostMapping("/create")
    public ResponseEntity<String> createData() {
        campingService.createCampingData();
        return ResponseEntity.ok("Create operation completed");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateData() {
        campingService.updateCampingData();
        return ResponseEntity.ok("Update operation completed");
    }
}
