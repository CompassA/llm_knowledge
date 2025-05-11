package org.tomato.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tomato
 * Created on 2025.05.11
 */
@RestController
public class TestController {

    @GetMapping("/api/knowledge/test/echo")
    public String echo(@RequestParam("param") String param) {
        return param;
    }
}
