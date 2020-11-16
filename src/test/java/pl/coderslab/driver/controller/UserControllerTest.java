package pl.coderslab.driver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import pl.coderslab.driver.dto.UserDto;
import pl.coderslab.driver.exceptions.UserNotFoundException;
import pl.coderslab.driver.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private UserDto testUserDto;
    private List<UserDto> testUsersList;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);

        testUserDto = new UserDto();
        testUserDto.setId(123L);
        testUserDto.setFirstName("FirstName");
        testUserDto.setLastName("LastName");
        testUserDto.setEmail("test@email.com");
        testUserDto.setUsername("Username");

        testUsersList = Collections.singletonList(testUserDto);
    }

    @Test
    void getAll() {
        when(userService.getAll()).thenReturn(testUsersList);

        ResponseEntity<List<UserDto>> allUsersToTest = userController.getAll();

        assertEquals(HttpStatus.OK, allUsersToTest.getStatusCode());
        assertEquals(testUsersList, allUsersToTest.getBody());
    }

    @Test
    void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsersList);

        ResponseEntity<List<UserDto>> allUsersToTest = userController.getAllUsers();

        assertEquals(HttpStatus.OK, allUsersToTest.getStatusCode());
        assertEquals(testUsersList, allUsersToTest.getBody());
    }

    @Test
    void getAllAdmins() {
        when(userService.getAllAdmins()).thenReturn(testUsersList);

        ResponseEntity<List<UserDto>> allUsersToTest = userController.getAllAdmins();

        assertEquals(HttpStatus.OK, allUsersToTest.getStatusCode());
        assertEquals(testUsersList, allUsersToTest.getBody());
    }

    @Test
    void findById() {
        when(userService.findById(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.findById(testUserDto.getId());

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());
    }

    @Test
    void findByIdFail() {
        when(userService.findById(any())).thenThrow(new UserNotFoundException(testUserDto.getId()));

        ResponseEntity<UserDto> userByIdToTest = userController.findById(testUserDto.getId());

        assertEquals(HttpStatus.NOT_FOUND, userByIdToTest.getStatusCode());
    }

    @Test
    void addUser() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.userRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());
    }

    @Test
    void addUserFailByBindingResultHasErrors() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(true);
        when(userService.userRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, userByIdToTest.getStatusCode());
    }

    @Test
    void addAdmin() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.adminRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addAdmin(testUserDto, testBindingResult);

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());
    }

    @Test
    void addAdminFailByBindingResultHasErrors() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(true);
        when(userService.adminRegistration(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.addAdmin(testUserDto, testBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, userByIdToTest.getStatusCode());
    }

    @Test
    void updateUserSuccess() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.update(any())).thenReturn(testUserDto);

        ResponseEntity<UserDto> userByIdToTest = userController.updateUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.OK, userByIdToTest.getStatusCode());
        assertEquals(testUserDto, userByIdToTest.getBody());

    }

    @Test
    void updateUserFailByUserNotFound() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(false);
        when(userService.update(any())).thenThrow(new UserNotFoundException(testUserDto.getId()));

        ResponseEntity<UserDto> userByIdToTest = userController.updateUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.NOT_FOUND, userByIdToTest.getStatusCode());
    }

    @Test
    void updateUserFailByBindingResultHasErrors() {
        BindingResult testBindingResult = mock(BindingResult.class);
        when(testBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<UserDto> userByIdToTest = userController.updateUser(testUserDto, testBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, userByIdToTest.getStatusCode());
    }

    @Test
    void delete() {
        userController.delete(1L);
        verify(userService, times(1)).delete(1L);
    }
}