package com.pharmacy.web;

import com.pharmacy.domain.User;
import com.pharmacy.exceptions.ControllerException;
import com.pharmacy.exceptions.type.ExceptionType;
import com.pharmacy.service.api.UserService;
import com.pharmacy.service.impl.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by apopow on 25.12.2015.
 */
@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Inject
    private UserService userService;

    @RequestMapping(value = "/login")
    public ModelAndView initLoginPage(@RequestParam(value = "error", required = false) String error,
                                      @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            try {
                getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION");
            } catch (ControllerException ex) {
                model.addObject("error", ex.getExceptionType().getResourceKey());
                ex.writeLog(LOG);
            }
        }
        if (logout != null) {
            model.setViewName("redirect:index");
            model.getModel();
        } else {
            model.setViewName("login");
        }
        return model;
    }

    // customize the error message#
    private void getErrorMessage(HttpServletRequest request, String key) throws ControllerException {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        if (exception != null) {
            throw new ControllerException(ExceptionType.LOGIN_0002, exception);
        } else {
            throw new ControllerException(ExceptionType.LOGIN_0003);
        }
    }


    @RequestMapping(value = "/login/passwort", method = RequestMethod.POST)
    private ModelAndView resetPasswort(@RequestParam String email){
        ModelAndView model = new ModelAndView("login");
        try {
            User user = userService.requestPasswordReset(email)
                    .orElseThrow(() -> new ControllerException(ExceptionType.RESET_PASSWORD_0001));
        } catch (ControllerException e) {
            e.writeLog(LOG);
            model.addObject("changePasswordError", e.getExceptionType().getResourceKey());
        }
        return model;
    }
}
