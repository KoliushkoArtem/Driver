package pl.coderslab.driver.converter;

import org.junit.jupiter.api.Test;
import pl.coderslab.driver.dto.UserDto;
import pl.coderslab.driver.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoConverterTest {

    private final Long id = 123L;
    private final String username = "Test Username";
    private final String firstName = "Test First Name";
    private final String lastName = "Test Last Name";
    private final String email = "Test@email.com";

    @Test
    void convertToUserDto() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        UserDto userDtoToTest = UserDtoConverter.convertToUserDto(user);

        assertEquals(id, userDtoToTest.getId());
        assertEquals(username, userDtoToTest.getUsername());
        assertEquals(firstName, userDtoToTest.getFirstName());
        assertEquals(lastName, userDtoToTest.getLastName());
        assertEquals(email, userDtoToTest.getEmail());
    }

    @Test
    void convertToUser() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername(username);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);

        User userToTest = UserDtoConverter.convertToUser(userDto);

        assertEquals(id, userToTest.getId());
        assertEquals(username, userToTest.getUsername());
        assertEquals(firstName, userToTest.getFirstName());
        assertEquals(lastName, userToTest.getLastName());
        assertEquals(email, userToTest.getEmail());
    }
}