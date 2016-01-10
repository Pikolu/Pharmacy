package com.pharmacy.web;

import com.pharmacy.config.SecurityUtils;
import com.pharmacy.domain.User;
import com.pharmacy.security.CustomUserDetails;
import com.pharmacy.service.api.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by Alexander on 09.11.2015.
 */
@Controller
public class IndexController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Inject
    private ImportService importService;

    @RequestMapping(value = {"/", "/index", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }


    @RequestMapping("/importtest")
    public void importTest(Model model) {
        Assert.notNull (model);
        importService.importCSVFile();
    }
}
