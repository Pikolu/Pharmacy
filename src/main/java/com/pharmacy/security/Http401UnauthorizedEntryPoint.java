package com.pharmacy.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);

    private static final String ACCOUNT_PATH = "/benutzerkonto";
    private static final String EVALUATION_PATH = "/bewertungen";
    private static final String PHARMACY_SEARCH_PATH = "/apotheken";

    /**
     * Always returns a 401 error code to the client.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
            throws IOException,
            ServletException {

        log.debug("Pre-authenticated entry point called. Rejecting access");
        if (ACCOUNT_PATH.equals(request.getRequestURI())
                || EVALUATION_PATH.equals(request.getRequestURI())
                || PHARMACY_SEARCH_PATH.equals(request.getRequestURI())) {
            response.sendRedirect("/login");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        }
    }
}
