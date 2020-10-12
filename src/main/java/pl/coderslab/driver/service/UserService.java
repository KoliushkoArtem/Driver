package pl.coderslab.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.exceptions.UserNotFoundException;
import pl.coderslab.driver.model.Role;
import pl.coderslab.driver.model.User;
import pl.coderslab.driver.repository.RoleRepository;
import pl.coderslab.driver.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository uSerRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = uSerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        User registeredUser = userRepository.save(user);

        log.info("IN userService register - user {} successfully registered", registeredUser);

        return registeredUser;
    }

    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        log.info("IN userService getAll - {} users found", allUsers.size());
        return allUsers;
    }

    public User findByUsername(String username) {
        User userFromDb = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        log.info("IN userService findByUsername - user: {} found by username: {}", userFromDb, username);
        return userFromDb;
    }

    public User findById(Long userId) {
        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("IN userService findById - user: {} found by Id: {}", userFromDb, userId);
        return userFromDb;
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
        log.info("IN userService delete - user: {} was successfully delete", userId);
    }

}
