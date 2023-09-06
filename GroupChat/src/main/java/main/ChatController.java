package main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ChatController {

    @GetMapping("/init")
    public HashMap<String, Boolean> init(){

        HashMap<String, Boolean> response = new HashMap<>();
        //TODO: check sessionId. If found => true, if not => false

        response.put("result", true);
        return response;
    }

    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@RequestParam String name){

        HashMap<String, Boolean> response = new HashMap<>();
        //TODO:
        // - create User with name, sessionId
        // - save User to DB

        response.put("result", true);
        return response;
    }

    @PostMapping("/message")
    public Boolean sendMessage(@RequestParam String message) {
        return true;
    }

    @GetMapping("/message")
    public List<String> getMessagesList(){
        return null;
    }

    @GetMapping("/user")
    public HashMap<Integer, String> getUserList(){
        return null;
    }
}
