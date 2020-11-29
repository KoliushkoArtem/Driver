package pl.driver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class Swagger2ConfigurationTest {

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("Test to check that Swagger configuration is not null")
    void swaggerConfigurationNotNullTest() {
        Swagger2Configuration swagger2Configuration = new Swagger2Configuration();

        assertNotNull(swagger2Configuration.swaggerConfiguration());
    }
}