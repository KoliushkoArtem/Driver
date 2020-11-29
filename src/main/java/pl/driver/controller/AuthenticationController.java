package pl.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.driver.dto.AuthenticationRequestDto;
import pl.driver.dto.AuthenticationResponseDto;
import pl.driver.dto.PasswordChangingDto;
import pl.driver.exceptions.JwtAuthenticationException;
import pl.driver.exceptions.PasswordMismatchException;
import pl.driver.exceptions.UserNotFoundException;
import pl.driver.model.User;
import pl.driver.security.jwt.JwtTokenProvider;
import pl.driver.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@Transactional
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login endpoint")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            String token = tokenProvider.createToken(username, user.getRoles());
            AuthenticationResponseDto responseDto = new AuthenticationResponseDto(username, token);

            return ResponseEntity.ok(responseDto);
        } catch (JwtAuthenticationException | UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/password/change")
    @ApiOperation(value = "Currently logged user password changing. Password length 8-20, should contain at leas 1 digit/uppercase/lowercase. NO whitespaces allowed")
    public ResponseEntity<String> currentUserPasswordChanging(@RequestBody @Valid PasswordChangingDto passwordChangingDto,
                                                              HttpServletRequest request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>("Incorrect new password", HttpStatus.BAD_REQUEST);
            } else {
                String token = tokenProvider.resolveToken(request);
                String username = tokenProvider.getUsername(token);
                userService.passwordUpdate(username, passwordChangingDto);
                return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
            }
        } catch (PasswordMismatchException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}