package pl.driver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("pl.driver"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Driver API",
                "Api for improving driving skills\nAll methods allowed for admins, for regular users allowed only GET methods" +
                        "\nTo use all methods in controllers you should be authorized\nTo test application you can login using bottom variants:" +
                        "\nFor login as ADMIN use username: admin, password: admin\nFor login as USER use username:user, password: user" +
                        "\n\nUsersController available only for admins and registration for using API available only on User request to Driver Administrators." +
                        "\nAfter registration User receive Email with username and randomly generated password",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Koliushko Artem", "https://www.linkedin.com/in/artem-koliushko/", "koliushko.a@gmail.com"),
                "API license",
                "https://www.linkedin.com/in/artem-koliushko/",
                Collections.emptyList());
    }
}