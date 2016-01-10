package com.pharmacy.web;

import com.pharmacy.config.SecurityUtils;
import com.pharmacy.security.CustomUserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Alexander on 02.01.2016.
 */
public class AbstractController {

    @ModelAttribute("customUser")
    public CustomUserDetails getCustomUserDetails() {
        return SecurityUtils.getCurrentUser();
    }
}
