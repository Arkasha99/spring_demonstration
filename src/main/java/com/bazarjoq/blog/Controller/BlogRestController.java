package com.bazarjoq.blog.Controller;

import com.bazarjoq.blog.models.Post;
import com.bazarjoq.blog.models.User;
import com.bazarjoq.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("blogapi")
public class BlogRestController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public Iterable<Post> blogMain(Model model){
        return(postRepository.findAll());
    }


    @GetMapping("{id}")
    public Post blogDetails(@PathVariable(value = "id") Long id){
        return (postRepository.findById(id).orElse(null));
    }

    @PostMapping
    public void addPost(@AuthenticationPrincipal User user,@RequestBody Post post){
        post.setAuthor(user);
        postRepository.save(post);
    }

    @PutMapping("{id}")
    public void changePost(@PathVariable(value = "id") Long id,@RequestBody Post post){
        Post changedPost = postRepository.findById(id).orElse(null);
        changedPost.setText(post.getText());
        changedPost.setAnnounce(post.getAnnounce());
        changedPost.setName(post.getName());
        changedPost.setAuthor(post.getAuthor());
        changedPost.setId(post.getId());
        postRepository.save(changedPost);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable(value = "id") Long id){
        Post post = postRepository.findById(id).orElse(null);
        postRepository.delete(post);
    }
}
