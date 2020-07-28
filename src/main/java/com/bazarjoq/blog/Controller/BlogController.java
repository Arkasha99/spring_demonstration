package com.bazarjoq.blog.Controller;


import com.bazarjoq.blog.models.Post;
import com.bazarjoq.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("blog")
    public String showBlog(Model model){
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return ("blog-add");
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String name, @RequestParam String announce, @RequestParam String text, Model model){
        Post post = new Post(name,announce,text);
        postRepository.save(post);
        return ("redirect:/blog");
    }


    @GetMapping("blog/{id}")
    public String showBlogDetails(@PathVariable(value = "id") Long id, Model model){
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-detail";
    }

    @GetMapping("blog/{id}/edit")
    public String blogEdit(@PathVariable(value="id") Long id, Model model){
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return ("blog-edit");
    }

    @PostMapping("blog/{id}/edit")
    public String blogEditPost(@PathVariable(value="id") Long id, @RequestParam String name, @RequestParam String announce, @RequestParam String text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setName(name);
        post.setAnnounce(announce);
        post.setText(text);
        postRepository.save(post);
        return ("redirect:/blog");
    }

    @GetMapping("blog/{id}/delete")
    public String blogDelete(@PathVariable(value="id") Long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return ("redirect:/blog");
    }
}
