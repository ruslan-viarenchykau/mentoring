package com.ruslan.mentoring.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class Servlet {
    @RequestMapping("/old")
    public String index(Map<String, Object> model, @RequestParam("index") int i) {
        model.put("index", i);
        return "welcome";
    }
}
