package com.olgac.examples.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"default", "local", "dev"})
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.olgac"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Demo API",
                "Demo API for Testing",
                "V1",
                applicationName,
                new Contact("Ahmet Vehbi Olgaç", "", "ahmetvehbiolgac@gmail.com"),
                "Health ?",
                "health"
        );
    }
}