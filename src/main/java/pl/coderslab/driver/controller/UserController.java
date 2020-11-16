package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.UserDto;
import pl.coderslab.driver.exceptions.UserNotFoundException;
import pl.coderslab.driver.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Transactional
@ApiOperation(value = "Secured, only for admins")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get all registered users and admins")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> allUsers = userService.getAll();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("get/users")
    @ApiOperation(value = "Get all registered users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("get/admins")
    @ApiOperation(value = "Get all registered admins")
    public ResponseEntity<List<UserDto>> getAllAdmins() {
        List<UserDto> allUsers = userService.getAllAdmins();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "Get user by id")
    public ResponseEntity<UserDto> findById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add/user")
    @ApiOperation(value = "Create user")
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new UserDto(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userService.userRegistration(userDto));
    }

    @PostMapping("/add/admin")
    @ApiOperation(value = "Create admin")
    public ResponseEntity<UserDto> addAdmin(@RequestBody @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new UserDto(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userService.adminRegistration(userDto));
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update user or admin")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new UserDto(), HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.ok(userService.update(userDto));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete user")
    public void delete(@PathVariable(name = "id") Long id) {
        userService.delete(id);
    }
}