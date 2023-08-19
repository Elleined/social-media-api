package com.elleined.forumapi.controller;

import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.service.ModalTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/modalTracker")
@CrossOrigin(origins = "*") // Allow other ports to access these endpoints
public class ModalTrackerController {

    private final ModalTrackerService modalTrackerService;

    @DeleteMapping("/deleteTracker")
    public ResponseEntity<ModalTracker> deleteTrackerByUserId(@PathVariable("currentUserId") int currentUserId,
                                                              @RequestParam("type") String type) {
        modalTrackerService.deleteTrackerOfUserById(currentUserId, ModalTracker.Type.valueOf(type));
        return ResponseEntity.noContent().build();
    }
}
