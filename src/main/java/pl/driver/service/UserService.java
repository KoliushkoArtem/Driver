package pl.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.driver.converter.UserDtoConverter;
import pl.driver.dto.PasswordChangingDto;
import pl.driver.dto.UserDto;
import pl.driver.exceptions.PasswordMismatchException;
import pl.driver.exceptions.UserNotFoundException;
import pl.driver.model.Role;
import pl.driver.model.User;
import pl.driver.repository.RoleRepository;
import pl.driver.repository.UserRepository;
import pl.driver.utils.EmailPatterns;
import pl.driver.utils.PasswordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    private static final String USER_ROLE_NAME = "ROLE_USER";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public UserDto userRegistration(UserDto userDto) {
        User registeredUser = registration(userDto, USER_ROLE_NAME);
        log.info("IN userService userRegistration - user: {} was successfully registered", registeredUser);
        return UserDtoConverter.convertToUserDto(registeredUser);
    }

    public UserDto adminRegistration(UserDto userDto) {
        User registeredAdmin = registration(userDto, ADMIN_ROLE_NAME);
        log.info("IN userService adminRegistration - admin: {} was successfully registered", registeredAdmin);
        return UserDtoConverter.convertToUserDto(registeredAdmin);
    }

    public List<UserDto> getAll() {
        List<UserDto> allUsers = userRepository.findAll()
                .stream()
                .map(UserDtoConverter::convertToUserDto)
                .collect(Collectors.toList());

        log.info("IN userService getAll - {} users found", allUsers.size());

        return allUsers;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> allUsers = userRepository.findAll()
                .stream()
                .filter(a -> a.getRoles().contains(roleRepository.findByName(USER_ROLE_NAME)))
                .map(UserDtoConverter::convertToUserDto)
                .collect(Collectors.toList());

        log.info("IN userService getAll - {} users found", allUsers.size());

        return allUsers;
    }

    public List<UserDto> getAllAdmins() {
        List<UserDto> allAdmins = userRepository.findAll()
                .stream()
                .filter(a -> a.getRoles().contains(roleRepository.findByName(ADMIN_ROLE_NAME)))
                .map(UserDtoConverter::convertToUserDto)
                .collect(Collectors.toList());

        log.info("IN userService getAll - {} users found", allAdmins.size());

        return allAdmins;
    }

    public User findByUsername(String username) throws UserNotFoundException {
        User userFromDb = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        log.info("IN userService findByUsername - user: {} found by username: {}", userFromDb, username);
        return userFromDb;
    }

    public UserDto findById(Long userId) throws UserNotFoundException {
        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("IN userService findById - user: {} found by Id: {}", userFromDb, userId);
        return UserDtoConverter.convertToUserDto(userFromDb);
    }

    public UserDto update(UserDto userDto) throws UserNotFoundException {
        User userToUpdate = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setUsername(userDto.getUsername());

        User updatedUser = userRepository.save(userToUpdate);
        log.info("IN userService update - user: {} was successfully updated", updatedUser);

        emailService.sendEmail(updatedUser.getEmail(),
                EmailPatterns.USER_UPDATE_SUBJECT.getValue(),
                EmailPatterns.USER_UPDATE_TEXT.getValue(updatedUser.getUsername(), updatedUser.getPassword()));

        return UserDtoConverter.convertToUserDto(updatedUser);
    }

    public void delete(Long userId) {
        User userFromDb = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(userFromDb);

        log.info("IN userService delete - user: {} was successfully delete", userId);
    }

    public void passwordUpdate(String currentPrincipalName, PasswordChangingDto passwordChangingDto) throws UserNotFoundException {
        User userToUpdate = userRepository.findByUsername(currentPrincipalName)
                .orElseThrow(() -> new UserNotFoundException(currentPrincipalName));
        if (passwordEncoder.matches(passwordChangingDto.getCurrentPassword(), userToUpdate.getPassword())) {
            userToUpdate.setPassword(passwordEncoder.encode(passwordChangingDto.getNewPassword()));

            User updatedUser = userRepository.save(userToUpdate);
            emailService.sendEmail(updatedUser.getEmail(),
                    EmailPatterns.USER_PASSWORD_CHANGING_SUBJECT.getValue(),
                    EmailPatterns.USER_PASSWORD_CHANGING_TEXT.getValue());

            log.info("IN userService passwordUpdate - user: {} was successfully changed password", updatedUser);
        } else {
            throw new PasswordMismatchException("You have provided incorrect current password");
        }
    }

    private User registration(UserDto userDto, String roleName) {
        User user = UserDtoConverter.convertToUser(userDto);
        Role userRole = roleRepository.findByName(roleName);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        String password = PasswordGenerator.generatePassword();
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        User registeredUser = userRepository.save(user);

        log.info("IN userService registration - user {} successfully registered", registeredUser);

        switch (roleName) {
            case USER_ROLE_NAME:
                emailService.sendEmail(registeredUser.getEmail(),
                        EmailPatterns.USER_REGISTRATION_SUBJECT.getValue(),
                        EmailPatterns.USER_REGISTRATION_TEXT.getValue(registeredUser.getUsername(), password));
                break;
            case ADMIN_ROLE_NAME:
                emailService.sendEmail(registeredUser.getEmail(),
                        EmailPatterns.ADMIN_REGISTRATION_SUBJECT.getValue(),
                        EmailPatterns.ADMIN_REGISTRATION_TEXT.getValue(registeredUser.getUsername(), password));
                break;
        }

        return registeredUser;
    }
}