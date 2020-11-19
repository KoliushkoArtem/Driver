package pl.coderslab.driver.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class Swagger2ConfigurationTest {

    @Test
    void swaggerConfigurationNotNullTest() {
        Swagger2Configuration swagger2Configuration = new Swagger2Configuration();

        assertNotNull(swagger2Configuration.swaggerConfiguration());
    }
}