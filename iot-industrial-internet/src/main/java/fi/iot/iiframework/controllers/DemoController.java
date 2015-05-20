/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {
    
    @RequestMapping("/")
    @ResponseBody
    String hello() {
        return "hello world!";
    }

}
