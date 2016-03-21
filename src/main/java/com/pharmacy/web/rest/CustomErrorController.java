package com.pharmacy.web.rest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

/**
 * Pharmacy GmbH
 * Created by Alexander on 21.03.2016.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
