package pl.driver.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class PasswordGeneratorTest {

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call generatePassword method assert that password will math to pattern \"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\\\s).{12}$\"")
    void generatePasswordPatternMatch() {
        String testPassword = PasswordGenerator.generatePassword();

        assertEquals(12, testPassword.length());
        assertTrue(testPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{12}$"));
    }
}