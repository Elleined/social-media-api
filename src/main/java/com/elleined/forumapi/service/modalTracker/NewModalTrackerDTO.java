package com.elleined.forumapi.service.modalTracker;

import com.elleined.forumapi.model.ModalTracker;

public class NewModalTrackerDTO {
    private int receiverId;
    private ModalTracker.Type type;
    private int associatedTypeIdOpened;

    public NewModalTrackerDTO() {
    }

    public NewModalTrackerDTO(int receiverId, ModalTracker.Type type, int associatedTypeIdOpened) {
        this.receiverId = receiverId;
        this.type = type;
        this.associatedTypeIdOpened = associatedTypeIdOpened;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public ModalTracker.Type getType() {
        return type;
    }

    public void setType(ModalTracker.Type type) {
        this.type = type;
    }

    public int getAssociatedTypeIdOpened() {
        return associatedTypeIdOpened;
    }

    public void setAssociatedTypeIdOpened(int associatedTypeIdOpened) {
        this.associatedTypeIdOpened = associatedTypeIdOpened;
    }
}
