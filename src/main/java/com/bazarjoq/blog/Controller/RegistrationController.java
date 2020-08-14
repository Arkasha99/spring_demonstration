package com.bazarjoq.blog.Controller;

import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,@RequestParam String firstName,@RequestParam String surname,@RequestParam String email, Model model){
        if (userService.addUser(user, firstName, surname,email) == null){
            model.addAttribute("message","user exists");
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model){
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("message", "successfully activated");
        } else{
            model.addAttribute("message", "activation code is not found");

        }

        return "login";
    }
}
