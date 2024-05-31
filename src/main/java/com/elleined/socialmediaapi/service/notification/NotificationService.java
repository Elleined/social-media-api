package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService<T extends Notification> {
    List<T> getAll(User currentUser, Pageable pageable);
    T getById(int id) throws ResourceNotFoundException;
    void read(T notification);
}
