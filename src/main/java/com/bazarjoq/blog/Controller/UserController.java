package com.bazarjoq.blog.Controller;


import com.bazarjoq.blog.models.Role;
import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "userlist";
    }

    @GetMapping("{id}")
    public String userEditForm(@PathVariable(value = "id") Long id, Model model){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> users = new ArrayList<>();
        user.ifPresent(users::add);
        model.addAttribute("user",users.get(0));
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PostMapping()
    public String userSave(@RequestParam String username,
                           @RequestParam String firstname,
                           @RequestParam String surname,
                           @RequestParam("role") List<String> form,
                           @RequestParam("userId") Long id){
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.getRole().clear();
        Set<String> role = Arrays.stream(Role.values())
                            .map(Role::name)
                            .collect(Collectors.toSet());
        for (String key : form) {
            if (role.contains(key)) {
                user.getRole().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:user";
    }
}
