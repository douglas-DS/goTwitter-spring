package org.gotwitter.endpoint.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IndexController {
    @GetMapping
    public String mainIndex() {
        String cmd = "hostname";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String stringProcess = process == null ? "" : process.getOutputStream().toString();
        return "<h3>Greetings from spring</h3>" +
                "<b>Hostname: <b/>" + stringProcess + "<br/>";
    }
}
