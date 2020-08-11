package com.bazarjoq.blog.Controller;

import com.bazarjoq.blog.models.Role;
import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,@RequestParam String firstName,@RequestParam String surname, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            model.addAttribute("message","user exists");
            return "registration";
        }
        user.setFirstname(firstName);
        user.setSurname(surname);
        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/";
    }
}
