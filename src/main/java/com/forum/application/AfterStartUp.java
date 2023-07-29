package com.forum.application;

import com.forum.application.service.ModalTrackerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    private final ModalTrackerService modalTrackerService;
    @PostConstruct
    void init() {
        modalTrackerService.deleteAll();
    }
}
