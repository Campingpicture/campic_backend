package team.campic.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.campic.collector.service.EventService;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<String> createEventData() {
        eventService.createEventData();
        return ResponseEntity.ok("Event data created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEventData() {
        eventService.updateEventData();
        return ResponseEntity.ok("Event data updated");
    }
}
