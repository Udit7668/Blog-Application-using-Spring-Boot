package com.udit.blogapplication.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.repository.PostRepository;
import com.udit.blogapplication.services.PostService;

@RestController
@RequestMapping("/getPosts")
public class PostControllerRestApi {

    @Autowired
    private PostService postService;
    
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Integer id){ 
    Post post=this.postService.getPostById(id);     
    return post;
    }

    @GetMapping("/")
    public List<Post> getAllPost(){
        List<Post> posts=this.postService.getAllPost();
        return posts;
    }

    @PostMapping("/add")
   public Post addPost(@RequestBody Post post){
    return null;
   }

}
