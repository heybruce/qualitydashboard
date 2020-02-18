package com.solera.apac1.qualitydashboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestRunController {

    private static final Logger logger = LoggerFactory.getLogger(TestRunController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public ModelAndView sample() {
        return new ModelAndView("sample");
    }

}
