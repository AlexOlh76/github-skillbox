package main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public final class DefaultController {

    @GetMapping("/")
    public String index(){
        return (new Date()).toString();
    }


}
