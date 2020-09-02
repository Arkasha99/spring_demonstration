package com.bazarjoq.blog.service;


import com.bazarjoq.blog.models.Role;
import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    public User addUser(User user, String firstName, String surname, String email){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFirstname(firstName);
        user.setSurname(surname);
        user.setActive(true);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setRole(Collections.singleton(Role.USER));
        userRepository.save(user);
        sendMessage(user);
        return user;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n "+
                            "Welcome to BlogHub. Please, visit the link to register you account: http://127.0.0.1:8080/activate/%s",
                    user.getFirstname(),
                    user.getActivationCode()
            );
            mailService.send(user.getEmail(), "Activation code", message);
        }
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void userSave(User user, String username, String firstname, String surname, List<String> form) {
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
    }

    public void editProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged =  ((email != null && !email.equals(userEmail))||(userEmail != null && !userEmail.equals(email)));
        if (isEmailChanged){
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);
        if (isEmailChanged){
             sendMessage(user);
        }
    }
}
