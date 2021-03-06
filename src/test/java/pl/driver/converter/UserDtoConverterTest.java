package pl.driver.converter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.driver.dto.UserDto;
import pl.driver.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class UserDtoConverterTest {

    private final Long id = 123L;
    private final String username = "Test Username";
    private final String firstName = "Test First Name";
    private final String lastName = "Test Last Name";
    private final String email = "Test@email.com";

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call convertToUserDto method assert that all similar fields would be an equals in incoming User and outputted UserDto")
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
    @DisplayName("When call convertToUser method assert that all similar fields would be an equals in incoming UserDto and outputted User")
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