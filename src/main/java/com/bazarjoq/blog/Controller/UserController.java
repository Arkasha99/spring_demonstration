package com.bazarjoq.blog.Controller;


import com.bazarjoq.blog.models.Role;
import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.UserRepository;
import com.bazarjoq.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "userlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{id}")
    public String userEditForm(@PathVariable(value = "id") Long id, Model model){
        User user = userService.findById(id).get();
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public String userSave(@RequestParam String username,
                           @RequestParam String firstname,
                           @RequestParam String surname,
                           @RequestParam("role") List<String> form,
                           @RequestParam("userId") Long id){
        User user = userService.findById(id).get();
        userService.userSave(user,username,firstname,surname,form);
        return "redirect:user";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email",user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String editProfile(@AuthenticationPrincipal User user, @RequestParam String password, @RequestParam String email){
        userService.editProfile(user, password, email);
        return "redirect:profile";
    }
}
