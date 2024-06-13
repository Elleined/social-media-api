package com.elleined.socialmediaapi.populator;

import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserPopulator implements Populator {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void populate() {
        Faker faker = new Faker();

        User user1 = userMapper.toEntity(faker.name().fullName(),
                faker.bothify("???##@gmail.com"),
                faker.avatar().image());

        User user2 = userMapper.toEntity(faker.name().fullName(),
                faker.bothify("???##@gmail.com"),
                faker.avatar().image());

        User user3 = userMapper.toEntity(faker.name().fullName(),
                faker.bothify("???##@gmail.com"),
                faker.avatar().image());

        User user4 = userMapper.toEntity(faker.name().fullName(),
                faker.bothify("???##@gmail.com"),
                faker.avatar().image());

        userRepository.saveAll(List.of(user1, user2, user3, user4));
    }
}
