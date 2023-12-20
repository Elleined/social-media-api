package com.elleined.forumapi;

import com.elleined.forumapi.service.ModalTrackerService;
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
