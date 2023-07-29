package com.forum.application.service;

import com.forum.application.model.ModalTracker;
import com.forum.application.repository.ModalTrackerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ModalTrackerService {

    private final ModalTrackerRepository modalTrackerRepository;

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public ModalTracker saveTrackerOfUserById(int receiverId, int associateTypeIdOpened, String type) {
        ModalTracker modalTracker = ModalTracker.builder()
                .receiverId(receiverId)
                .associatedTypeIdOpened(associateTypeIdOpened)
                .type(ModalTracker.Type.valueOf(type))
                .build();

        var saveModalTracker = modalTrackerRepository.save(modalTracker);
        log.debug("Saving modal tracker for the receiver with id of {} and Type of {} with associated id of {} success!", receiverId, type, associateTypeIdOpened);
        return saveModalTracker;
    }

    public ModalTracker getTrackerOfUserById(int userId) {
        return modalTrackerRepository.findById(userId).orElse(null);
    }
    
    public void deleteTrackerOfUserById(int userId, ModalTracker.Type type) {
        ModalTracker modalTracker = getTrackerOfUserById(userId);
        if (modalTracker == null) return;
        if (modalTracker.getType() == type) {
            modalTrackerRepository.deleteById(userId);
            log.debug("Deleting modal tracker for receiver with id of {} success!", userId);
        }
    }

    boolean isModalOpen(int receiverId, int associatedTypeId, ModalTracker.Type type) {
        ModalTracker modalTracker = this.getTrackerOfUserById(receiverId);
        if (modalTracker == null) return false;
        return modalTracker.getType() == type &&
                modalTracker.getAssociatedTypeIdOpened() == associatedTypeId &&
                modalTracker.getReceiverId() == receiverId;
    }

    public void deleteAll() {
        modalTrackerRepository.deleteAll();
        log.debug("Deleting all the record in modal tracked success!");
    }
}
