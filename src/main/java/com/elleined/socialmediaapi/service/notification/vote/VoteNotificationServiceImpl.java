package com.elleined.socialmediaapi.service.notification.vote;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.vote.VoteNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.vote.VoteNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import com.elleined.socialmediaapi.repository.notification.vote.VoteNotificationRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VoteNotificationServiceImpl implements VoteNotificationService {
    private final UserRepository userRepository;

    private final VoteNotificationRepository voteNotificationRepository;
    private final VoteNotificationMapper voteNotificationMapper;

    @Override
    public List<VoteNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllVoteNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public VoteNotification getById(int id) throws ResourceNotFoundException {
        return voteNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, VoteNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        voteNotificationRepository.save(notification);
    }

    @Override
    public VoteNotification save(User currentUser, Vote vote) {
        VoteNotification voteNotification = voteNotificationMapper.toEntity(currentUser, vote);

        voteNotificationRepository.save(voteNotification);
        log.debug("Saving vote notification success");
        return voteNotification;
    }
}
