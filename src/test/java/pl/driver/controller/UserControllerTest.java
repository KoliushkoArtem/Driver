package pl.driver.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import pl.driver.dto.UserDto;
import pl.driver.exceptions.UserNotFoundException;
import pl.driver.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private UserDto testUserDto;
    private List<UserDto> testUsersList;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        userService = mock(UserService.class);
        userController = new UserController(userService);

        testUserDto = new UserDto();
        testUserDto.setId(123L);
        testUserDto.setFirstName("FirstName");
        testUserDto.setLastName("LastName");
        testUserDto.setEmail("test@email.com");
        testUserDto.setUsername("Username");

        testUsersList = Collections.singletonList(testUserDto);

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call getAll method assert List with UserDto and HTTP status OK")
    void getAll() {
        when(userService.getAll()).thenReturn(testUsersList);

        ResponseEntity<List<UserDto>> allUsersToTest = userController.getAll();

        assertEquals(HttpStatus.OK, allUsersToTest.getStatusCode());
        assertEquals(testUsersList, allUsersToTest.getBody());
    }

    @Test
    @DisplayName("When call getAllUsers method assert List with UserDto and HTTP status OK")
    void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsersList);

        ResponseEntity<List<UserDto>> allUsersToTest = userController.getAllUsers();

        assertEquals(HttpStatus.OK, allUsersToTest.getStatusCode());
        assertEquals(testUsersList, allUsersToTest.getBody());
    }

    @Test
    @DisplayName("When call getAllAdmins method assert List with UserDto and HTTP status OK")
    void getAllAdmins() {
        when(userService.getAllAdmins()).thenReturn(testUsersList);

        ResponseEntity<List<UserDto>> allUsersToTest = userController.getAllAdmins();

        assertEquals(HttpStatus.OK, allUsersToTest.getStatusCode());
        assertEquals(testUsersList, allUsersToTest.getBody());
    }

    @Test
    @DisplayName("When call findById method with exist id assert ResponseEntity with UserDto and HTTP status OK")
    void findById() {
        when(userService.findById(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.findById(testUserDto.getId());

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());
    }

    @Test
    @DisplayName("When call findById method with not exist id assert ResponseEntity with HTTP status NOT_FOUND")
    void findByIdFail() {
        when(userService.findById(any())).thenThrow(new UserNotFoundException(testUserDto.getId()));

        ResponseEntity<UserDto> userByIdToTest = userController.findById(testUserDto.getId());

        assertEquals(HttpStatus.NOT_FOUND, userByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call addUser method with BindingResult has no errors assert ResponseEntity with saved UserDto and HTTP status OK")
    void addUser() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.userRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());
    }

    @Test
    @DisplayName("When call addUser method with BindingResult has errors assert ResponseEntity with HTTP status BAD_REQUEST")
    void addUserFailByBindingResultHasErrors() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(true);
        when(userService.userRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, userByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call addAdmin method with BindingResult has no errors assert ResponseEntity with saved UserDto and HTTP status OK")
    void addAdmin() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.adminRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addAdmin(testUserDto, testBindingResult);

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());
    }

    @Test
    @DisplayName("When call addAdmin method with BindingResult has errors assert ResponseEntity with HTTP status BAD_REQUEST")
    void addAdminFailByBindingResultHasErrors() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(true);
        when(userService.adminRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addAdmin(testUserDto, testBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, userByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call updateUser method with BindingResult has no errors and exist user assert ResponseEntity with saved UserDto and HTTP status OK")
    void updateUserSuccess() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.update(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.updateUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());

    }

    @Test
    @DisplayName("When call updateUser method with BindingResult has no errors but not exist user assert ResponseEntity with HTTP status NOT_FOUND")
    void updateUserFailByUserNotFound() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.update(any())).thenThrow(new UserNotFoundException(testUserDto.getId()));

        ResponseEntity<UserDto> userByIdToTest = userController.updateUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.NOT_FOUND, userByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call updateUser method with BindingResult has errors but exist user assert ResponseEntity with HTTP status BAD_REQUEST")
    void updateUserFailByBindingResultHasErrors() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<UserDto> userByIdToTest = userController.updateUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, userByIdToTest.getStatusCode());
    }

    //TODO
    @Test
    void delete() {
        userController.delete(1L);
        verify(userService, times(1)).delete(1L);
    }
}