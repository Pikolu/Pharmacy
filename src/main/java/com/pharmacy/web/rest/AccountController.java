package com.pharmacy.web.rest;

import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.User;
import com.pharmacy.security.CustomUserDetails;
import com.pharmacy.service.api.UserService;
import com.pharmacy.web.validator.ExistsUserValidator;
import com.pharmacy.web.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Alexander on 06.01.2016.
 */
@Controller
public class AccountController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Inject
    private UserValidator validator;
    @Inject
    private ExistsUserValidator existsUserValidator;
    @Inject
    private UserService userService;

    @RequestMapping(value = "/benutzerkonto", method = RequestMethod.GET)
    public String initAccount(Model model) {
        String page = null;
        CustomUserDetails userDetails = getCustomUserDetails();
        if (userDetails != null) {
            User user = userService.getUserWithAuthorities(userDetails.getId());
            model.addAttribute("userForm", user);
            page = "account";
        } else {
            page = "login";
        }
        model.addAttribute("searchResult", new SearchResult());
        return page;
    }

    @RequestMapping(value = "/benutzerkonto", method = RequestMethod.POST)
    public ModelAndView changeAccount(@ModelAttribute("userForm") User userForm, BindingResult result) {
        ModelAndView modelAndView;
        User oldUser = userService.getUser(getCustomUserDetails().getId());
        existsUserValidator.validate(oldUser, userForm, result);
        if (result.hasErrors()) {
            modelAndView = new ModelAndView("account", "command", userForm);
            modelAndView.getModel().putAll(result.getModel());
        } else {
            userService.updateUserInformation(userForm.getFirstName(), userForm.getLastName(), userForm.getEmail(), null);
            userService.changePassword(userForm.getPassword());
            modelAndView = new ModelAndView("account", "command", userForm);
        }
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }
}
