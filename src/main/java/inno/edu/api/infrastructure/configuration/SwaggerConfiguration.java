package inno.edu.api.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swagger() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage("inno.edu.api.controllers"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(200)
                                .message("200 OK")
                                .build()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "InnoEdu - API",
                "Plataform.",
                "1.0",
                "Terms of service",
                DEFAULT_CONTACT,
                "Private", "inno.edu", Collections.emptyList());
    }
}
