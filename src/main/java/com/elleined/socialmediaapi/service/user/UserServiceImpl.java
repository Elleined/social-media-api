package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.jwt.JWTService;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserServiceRestriction {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BlockService blockService;

    private final EmailValidator emailValidator;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getByJWT(String jwt) throws ResourceNotFoundException {
        String email = jwtService.getEmail(jwt);
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email of " + email + " doesn't exists!"));
    }

    @Override
    public User register(String name, String email, String password, String picture) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = userMapper.toEntity(name, email, hashedPassword, picture);
        userRepository.save(user);
        log.debug("User with id of {} saved successfully", user.getId());
        return user;
    }

    @Override
    public String login(String email,
                        String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (!authentication.isAuthenticated())
            throw new UsernameNotFoundException("Invalid credential");

        return jwtService.generateToken(email);
    }

    @Override
    public Set<User> getAllById(Set<Integer> ids) {
        return new HashSet<>(userRepository.findAllById(ids));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId +  " does not exists"));
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getByUUID(String UUID) throws ResourceNotFoundException {
        return userRepository.findAll().stream()
                .filter(user -> user.getUUID().equals(UUID))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + UUID +  " does not exists"));
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .anyMatch(email::equalsIgnoreCase);
    }


    @Override
    public Page<User> getAllSuggestedMentions(User currentUser, String name, Pageable pageable) {
        List<User> users = userRepository.findAllByName(name, pageable).stream()
                .filter(suggestedUser -> !suggestedUser.equals(currentUser))
                .filter(suggestedUser -> !blockService.isBlockedByYou(currentUser, suggestedUser))
                .filter(suggestedUser -> !blockService.isYouBeenBlockedBy(currentUser, suggestedUser))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), users.size());

        List<User> pageContent = users.subList(start, end);
        return new PageImpl<>(pageContent, pageable, users.size());
    }
}
