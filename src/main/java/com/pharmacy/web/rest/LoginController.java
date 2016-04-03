package com.pharmacy.web.rest;

import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.User;
import com.pharmacy.exceptions.ControllerException;
import com.pharmacy.exceptions.type.ExceptionType;
import com.pharmacy.service.api.MailService;
import com.pharmacy.service.api.UserService;
import com.pharmacy.service.impl.MailServiceImpl;
import com.pharmacy.web.dto.KeyAndPasswordDTO;
import com.pharmacy.web.validator.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Inject
    private MailServiceImpl mailService;
    @Inject
    private PasswordValidator passwordValidator;

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
            model.setViewName("redirect:welcome");
            model.getModel();
        } else {
            model.setViewName("login");
        }
        model.addObject("searchResult", new SearchResult());
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
    private ModelAndView resetPassword(@RequestParam String email, HttpServletRequest request){
        ModelAndView model = new ModelAndView("login");
        model.addObject("searchResult", new SearchResult());
        try {
            Optional<User> user = userService.requestPasswordReset(email);
            user.ifPresent(u -> {
                String baseUrl = request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort();
                mailService.sendPasswordResetMail(u, baseUrl);
            });
            user.orElseThrow(() -> new ControllerException(ExceptionType.RESET_PASSWORD_0001));
        } catch (ControllerException e) {
            e.writeLog(LOG);
            model.addObject("changePasswordError", e.getExceptionType().getResourceKey());
        }
        return model;
    }

    @RequestMapping(value = "/login/passwort/reset", method = RequestMethod.GET)
    private ModelAndView changePassword(String key, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("passwordReset", "resetForm", new KeyAndPasswordDTO(key));
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }

    @RequestMapping(value = "/login/passwort/reset", method = RequestMethod.POST)
    private ModelAndView changePassword(@ModelAttribute("resetForm") KeyAndPasswordDTO keyAndPasswordDTO, BindingResult result){
        ModelAndView modelAndView;
        passwordValidator.validate(keyAndPasswordDTO, result);
        if (result.hasErrors()) {
            modelAndView = new ModelAndView("passwordReset", "resetForm", keyAndPasswordDTO);
        } else {
            modelAndView = new ModelAndView("index");
            userService.completePasswordReset(keyAndPasswordDTO.getNewPassword(), keyAndPasswordDTO.getKey());
            modelAndView.addObject("passwordResetSuccessful", true);
        }
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }
}
