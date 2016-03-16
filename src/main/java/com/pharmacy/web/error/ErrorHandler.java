package com.pharmacy.web.error;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Pharmacy GmbH
 * Created by Alexander on 16.03.2016.
 */
@Component
public class ErrorHandler implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"),
                new ErrorPage(HttpStatus.BAD_REQUEST, "/404.html")
        );

    }
}
