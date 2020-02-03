package org.gotwitter.endpoint.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class IndexController {
    @GetMapping
    public String mainIndex() {
        String cmd = "hostname";
        Process process = null;
        String stringProcess = "";
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stringProcess = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<h3>Greetings from spring !!</h3>" +
                "<b>Hostname: <b/>" + stringProcess + "<br/>";
    }
}
