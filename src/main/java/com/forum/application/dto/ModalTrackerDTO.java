package com.forum.application.dto;

public record ModalTrackerDTO(
        int receiverId,
        String type,
        int associatedTypeIdOpened
) {
}
