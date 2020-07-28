package com.bazarjoq.blog.Controller;

import com.bazarjoq.blog.models.Post;
import com.bazarjoq.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class BlogRestController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("blogapi")
    public Iterable<Post> blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        return(posts);
    }


    @GetMapping("blogapi/{id}")
    public ArrayList<Post> blogDetails(@PathVariable(value = "id") Long id, Model model){
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        return (res);
    }
}
