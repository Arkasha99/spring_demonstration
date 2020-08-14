package com.bazarjoq.blog.Controller;


import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("title", "Главная страница");
        if (user != null){
            model.addAttribute("name",user.getFirstname()+" "+user.getSurname());
        }
        return "index";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("info","This is test");
        return "about";
    }

}
