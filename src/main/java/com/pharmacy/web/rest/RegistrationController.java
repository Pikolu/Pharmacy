package com.pharmacy.web.rest;

import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.User;
import com.pharmacy.service.api.UserService;
import com.pharmacy.service.impl.MailServiceImpl;
import com.pharmacy.web.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by apopow on 27.12.2015.
 */
@Controller
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private static final String REGISTRATION = "registration";
    @Inject
    private UserValidator validator;
    @Inject
    private UserService userService;
    private ModelAndView modelAndView;
    @Inject
    private MailServiceImpl mailService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("command") User user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        LOG.trace("Enter registration: user={}, result={}", user, result);
        validator.validate(user, result);
        if (result.hasErrors()) {
            if (modelAndView == null) {
                modelAndView = new ModelAndView("redirect:registration.html", "command", new User());
            }
            modelAndView.getModel().putAll(result.getModel());
        } else {
            modelAndView = new ModelAndView("redirect:welcome.html", "command", user);
            userService.createUserInformation(user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), "de_DE");
            String baseUrl = request.getScheme() + // "http"
                    "://" +                                // "://"
                    request.getServerName() +              // "myhost"
                    ":" +                                  // ":"
                    request.getServerPort();               // "80"

            mailService.sendActivationEmail(user, baseUrl);
//            authenticateUserAndSetSession(user, request);
        }
        LOG.trace("Exit registration: modelAndView={}", modelAndView);
        return modelAndView;
    }

    private void authenticateUserAndSetSession(User account, HttpServletRequest request) {
        LOG.trace("Enter authenticateUserAndSetSession: account={}", account);
        String username = account.getEmail();
        String password = account.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        LOG.trace("Exit authenticateUserAndSetSession");
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView initRegistration() {
        LOG.trace("Enter showContacts");
        modelAndView = new ModelAndView(REGISTRATION, "command", new User(""));
        modelAndView.addObject("searchResult", new SearchResult());
        LOG.trace("Exit initRegistration: modelAndView={}", modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/registration/aktivierung", method = RequestMethod.GET)
    public ModelAndView finishRegistration(@RequestParam(value = "key") String key, HttpServletRequest request) {
        modelAndView.addObject("searchResult", new SearchResult());
        return userService.activateRegistration(key).map(user -> {
            modelAndView = new ModelAndView("login");
            return modelAndView;
        }).orElseGet(() -> {
            modelAndView = new ModelAndView("redirect:registration.html", "command", new User());
            return modelAndView;
        });
    }

}
