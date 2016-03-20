package com.pharmacy.config;

import com.pharmacy.web.rest.CustomErrorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Pharmacy GmbH
 * Created by Alexander on 20.03.2016.
 */
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Configuration
public class ErrorConfiguration implements EmbeddedServletContainerCustomizer {

    private final Logger log = LoggerFactory.getLogger(ErrorConfiguration.class);

    private String errorPath = "/error";

    @Autowired
    private ServerProperties properties;

    @Bean
    public ErrorController basicErrorController(ErrorAttributes errorAttributes) {
        log.info("Initialize error controller with attributes {}", errorAttributes);
        return new CustomErrorController(errorAttributes);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(
                new ErrorPage(this.properties.getServletPrefix() + this.errorPath));
    }
}
