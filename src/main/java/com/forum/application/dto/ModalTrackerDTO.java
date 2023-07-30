package com.forum.application.dto;

import com.forum.application.model.ModalTracker;

public record ModalTrackerDTO(
        int receiverId,
        String type,
        int associatedTypeIdOpened
) {
}
