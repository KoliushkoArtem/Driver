package pl.driver.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.driver.model.User;
import pl.driver.security.jwt.JwtUser;
import pl.driver.security.jwt.JwtUserFactory;
import pl.driver.service.UserService;

@Service
@Slf4j
public class DriverUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public DriverUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userService.findByUsername(username);
        JwtUser jwtUser = JwtUserFactory.create(byUsername);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}