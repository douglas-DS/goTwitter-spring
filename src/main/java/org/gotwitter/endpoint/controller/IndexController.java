package org.gotwitter.endpoint.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String mainIndex() {
        Process process;
        String stringProcess = "";
        try {
            process = Runtime.getRuntime().exec("hostname");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stringProcess = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<h3>Greetings from goTwitter service !!</h3>" +
                "<b>Hostname (container id): <b/>" + stringProcess + "<br/>";
    }
}
