package com.forum.application.controller;

import com.forum.application.model.ModalTracker;
import com.forum.application.service.ModalTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/modalTracker")
public class ModalTrackerController {

    private final ModalTrackerService modalTrackerService;

    @PostMapping("/saveTracker")
    public ResponseEntity<ModalTracker> saveTracker(@PathVariable("currentUserId") int receiverId,
                                                    @RequestParam("associatedTypeId") int associateTypeId,
                                                    @RequestParam("type") String type) {

        ModalTracker modalTracker = modalTrackerService.saveTrackerOfUserById(receiverId, associateTypeId, type);
        return ResponseEntity.ok(modalTracker);
    }

    @DeleteMapping("/deleteTracker")
    public ResponseEntity<ModalTracker> deleteTrackerByUserId(@PathVariable("currentUserId") int currentUserId,
                                                              @RequestParam("type") String type) {
        modalTrackerService.deleteTrackerOfUserById(currentUserId, ModalTracker.Type.valueOf(type));
        return ResponseEntity.noContent().build();
    }
}
