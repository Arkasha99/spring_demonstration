package com.bazarjoq.blog.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "index";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("info","This is test");
        return "about";
    }

}
