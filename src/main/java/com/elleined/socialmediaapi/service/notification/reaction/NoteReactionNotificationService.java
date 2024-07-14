package com.elleined.socialmediaapi.service.notification.reaction;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.NoteReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.reaction.NoteReactionNotificationRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class NoteReactionNotificationService implements ReactionNotificationService<NoteReactionNotification, Note> {
    private final UserRepository userRepository;

    private final NoteReactionNotificationRepository noteReactionNotificationRepository;
    private final ReactionNotificationMapper reactionNotificationMapper;

    @Override
    public Page<NoteReactionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllNoteReactionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public NoteReactionNotification getById(int id) throws ResourceNotFoundException {
        return noteReactionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, NoteReactionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        noteReactionNotificationRepository.save(notification);
    }

    @Override
    public NoteReactionNotification save(User currentUser, Note note, Reaction reaction) {
        NoteReactionNotification noteReactionNotification = reactionNotificationMapper.toEntity(currentUser, note, reaction);

        noteReactionNotificationRepository.save(noteReactionNotification);
        log.debug("Saving note reaction notification success");
        return noteReactionNotification;
    }
}
