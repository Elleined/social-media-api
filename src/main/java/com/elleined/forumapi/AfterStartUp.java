package com.elleined.forumapi;

import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.ws.notification.like.PostLikeWSNotificationService;
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
