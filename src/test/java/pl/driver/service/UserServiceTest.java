package pl.driver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.driver.converter.UserDtoConverter;
import pl.driver.dto.PasswordChangingDto;
import pl.driver.dto.UserDto;
import pl.driver.exceptions.PasswordMismatchException;
import pl.driver.exceptions.UserNotFoundException;
import pl.driver.model.Role;
import pl.driver.model.User;
import pl.driver.repository.RoleRepository;
import pl.driver.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepositoryMock;
    private RoleRepository roleRepositoryMock;
    private BCryptPasswordEncoder passwordEncoderMock;
    private EmailService emailServiceMock;
    private UserService userService;
    private User testUser;
    private List<Role> userRoles;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        roleRepositoryMock = mock(RoleRepository.class);
        passwordEncoderMock = mock(BCryptPasswordEncoder.class);
        emailServiceMock = mock(EmailService.class);
        userService = new UserService(userRepositoryMock, roleRepositoryMock, passwordEncoderMock, emailServiceMock);

        testUser = new User();
        testUser.setId(123L);
        testUser.setFirstName("Test First Name");
        testUser.setLastName("Test Last Name");
        testUser.setEmail("test@email.com");
        testUser.setUsername("Test Username");
        testUser.setPassword("Super Password");
        userRoles = new ArrayList<>();
        Role roleAdmin = new Role();
        roleAdmin.setId(1L);
        roleAdmin.setName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleAdmin.setId(2L);
        roleAdmin.setName("ROLE_USER");
        userRoles.add(roleAdmin);
        userRoles.add(roleUser);
        testUser.setRoles(userRoles);

        testUserDto = UserDtoConverter.convertToUserDto(testUser);
    }

    @Test
    void userRegistration() {
        when(roleRepositoryMock.findByName(any())).thenReturn(userRoles.get(1));
        when(passwordEncoderMock.encode(anyString())).thenReturn("CodedPassword");
        when(userRepositoryMock.save(any())).thenReturn(testUser);

        UserDto userDtoToTest = userService.userRegistration(testUserDto);

        assertEquals(testUserDto, userDtoToTest);
        verify(emailServiceMock, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void adminRegistration() {
        when(roleRepositoryMock.findByName(any())).thenReturn(userRoles.get(0));
        when(passwordEncoderMock.encode(anyString())).thenReturn("CodedPassword");
        when(userRepositoryMock.save(any())).thenReturn(testUser);

        UserDto userDtoToTest = userService.adminRegistration(testUserDto);

        assertEquals(testUserDto, userDtoToTest);
        verify(emailServiceMock, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void getAll() {
        User[] users = new User[]{testUser};
        List<User> userList = Arrays.asList(users);

        when(userRepositoryMock.findAll()).thenReturn(userList);

        List<UserDto> testUsersList = userService.getAll();

        assertTrue(testUsersList.contains(testUserDto));
        assertEquals(1, testUsersList.size());
    }

    @Test
    void getAllUsers() {
        User[] users = new User[]{testUser};
        List<User> userList = Arrays.asList(users);

        when(userRepositoryMock.findAll()).thenReturn(userList);
        when(roleRepositoryMock.findByName(any())).thenReturn(userRoles.get(1));

        List<UserDto> testUsersList = userService.getAllUsers();

        assertTrue(testUsersList.contains(testUserDto));
        assertEquals(1, testUsersList.size());
    }

    @Test
    void getAllAdmins() {
        User[] users = new User[]{testUser};
        List<User> userList = Arrays.asList(users);

        when(userRepositoryMock.findAll()).thenReturn(userList);
        when(roleRepositoryMock.findByName(any())).thenReturn(userRoles.get(0));

        List<UserDto> testUsersList = userService.getAllAdmins();

        assertTrue(testUsersList.contains(testUserDto));
        assertEquals(1, testUsersList.size());
    }

    @Test
    void findByUsername() {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.ofNullable(testUser));

        User userFromDbToTest = userService.findByUsername(testUserDto.getUsername());

        assertEquals(testUser, userFromDbToTest);
    }

    @Test
    void findByUsernameFail() {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(anyString()));
    }

    @Test
    void findById() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(testUser));

        UserDto userFromDbToTest = userService.findById(anyLong());

        assertEquals(testUserDto, userFromDbToTest);
    }

    @Test
    void findByIdFail() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(anyLong()));
    }

    @Test
    void update() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(testUser));
        when(userRepositoryMock.save(any())).thenReturn(testUser);

        UserDto userDtoToTest = userService.update(testUserDto);

        assertEquals(testUserDto, userDtoToTest);
        verify(emailServiceMock, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void updateFail() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(testUserDto));
    }

    @Test
    void delete() {
        userService.delete(1L);

        verify(userRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void passwordUpdate() {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.ofNullable(testUser));
        when(passwordEncoderMock.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoderMock.encode(anyString())).thenReturn("CodedPassword");
        when(userRepositoryMock.save(any())).thenReturn(testUser);

        PasswordChangingDto passwordChangingDto = new PasswordChangingDto();
        passwordChangingDto.setCurrentPassword("Current");
        passwordChangingDto.setNewPassword("New");

        userService.passwordUpdate(anyString(), passwordChangingDto);

        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verify(passwordEncoderMock, times(1)).matches(anyString(), anyString());
        verify(passwordEncoderMock, times(1)).encode(anyString());
        verify(userRepositoryMock, times(1)).save(testUser);
        verify(emailServiceMock, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void passwordUpdateFailByUserId() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.passwordUpdate(anyString(), new PasswordChangingDto()));
    }

    @Test
    void passwordUpdateFailByCurrentPassword() {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.ofNullable(testUser));
        when(passwordEncoderMock.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(PasswordMismatchException.class, () -> userService.passwordUpdate(anyString(), new PasswordChangingDto()));
    }
}