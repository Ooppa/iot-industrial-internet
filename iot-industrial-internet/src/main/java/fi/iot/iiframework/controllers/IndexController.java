/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.controllers;

import fi.iot.iiframework.views.ViewParams;
import fi.iot.iiframework.views.ViewUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        ViewParams params = new ViewParams("Hello world!", "Tervetuloa Springiin!");

        if (true) { // if user logged in etc...
            params.setNavtype("loggedin");
        }

        ViewUtils.addViewParamsToModel(model, params);

        return "default";
    }

    @RequestMapping("/ng")
    public String ng(Model model) {
        ViewParams params = new ViewParams("Hello world!", "Tervetuloa Springiin!");
        ViewUtils.addViewParamsToModel(model, params);
        return "ng";
    }
}
