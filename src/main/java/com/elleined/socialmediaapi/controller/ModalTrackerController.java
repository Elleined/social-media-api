package com.elleined.socialmediaapi.controller;

import com.elleined.socialmediaapi.service.mt.ModalTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/modalTracker")
public class ModalTrackerController {

    private final ModalTrackerService modalTrackerService;

    @DeleteMapping("/deleteTracker")
    public ResponseEntity<ModalTracker> deleteTrackerByUserId(@PathVariable("currentUserId") int currentUserId,
                                                              @RequestParam("type") String type) {
        modalTrackerService.deleteTrackerOfUserById(currentUserId, ModalTracker.Type.valueOf(type));
        return ResponseEntity.noContent().build();
    }
}
