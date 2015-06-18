/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.controllers;

import fi.iot.iiframework.views.ViewParams;
import fi.iot.iiframework.views.ViewUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Secured({"ROLE_VIEWER", "ROLE_MODERATOR"})
    @RequestMapping("/")
    public String index(Model model) {
        ViewParams params = new ViewParams("IIFramework", "Tervetuloa ng-Springiin!");
        ViewUtils.addViewParamsToModel(model, params);
        return "ng";
    }

}
