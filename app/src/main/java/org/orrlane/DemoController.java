package org.orrlane;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String perform() {
        return "hello world";
    }

}
