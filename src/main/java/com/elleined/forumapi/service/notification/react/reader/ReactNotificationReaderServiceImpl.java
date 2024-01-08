package com.elleined.forumapi.service.notification.react.reader;

import com.elleined.forumapi.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReactNotificationReaderServiceImpl implements ReactNotificationReaderService {
    @Override
    public void readAll(User currentUser) {

    }
}
