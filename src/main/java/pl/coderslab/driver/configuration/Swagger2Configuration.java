package pl.coderslab.driver.configuration;

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
                .paths(PathSelectors.ant("/api/*"))
                .apis(RequestHandlerSelectors.basePackage("pl.coderslab.driver.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Driver API",
                "Api for improving driving skills",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Koliushko Artem","https://www.linkedin.com/in/artem-koliushko-7bb8a2106/", "koliushko.a@gmail.com"),
                "API license",
                "https://www.linkedin.com/in/artem-koliushko-7bb8a2106/",
                Collections.emptyList());
    }
}
