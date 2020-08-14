package com.bazarjoq.blog.service;


import com.bazarjoq.blog.models.Role;
import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    public User addUser(User user, String firstName, String surname, String email){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            return null;
        }
        user.setFirstname(firstName);
        user.setSurname(surname);
        user.setActive(true);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setRole(Collections.singleton(Role.USER));

        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n "+
                            "Welcome to BlogHub. Please, visit the link to register you account: http://127.0.0.1:8080/activate/%s",
                    user.getFirstname(),
                    user.getActivationCode()
            );
            mailService.send(user.getEmail(), "Activation code", message);
        }
        userRepository.save(user);
        return user;
    }


    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }
        user.setActivationCode(code);
        userRepository.save(user);
        return true;
    }
}
