package pl.driver.exceptions;

public class UserNotFoundException extends RuntimeException {

    private String username;

    private Long userId;

    public UserNotFoundException(String username) {
        this.username = username;
    }

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        if (username != null && !username.isBlank()) {
            return "User with username: " + username + " was not found";
        } else {
            return "User with Id: " + userId + " was not found";
        }
    }
}