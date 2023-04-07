package com.example.fistproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username","KH");
        return "greetings"; // templates/greetings.mustache >> 브라우저로 전송해줌;
    }

    @GetMapping("/bye")
    public String byeBye(Model model){
        model.addAttribute("nickname","KH");
        return "goodbye";
    }
}
