package com.udit.blogapplication.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.repository.PostRepository;

@RestController
@RequestMapping("/getPosts")
public class PostControllerRestApi {

    @Autowired
    private PostRepository postRepository;
    
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Integer id){ 
    Post post=(Post) this.postRepository.findPostById(id);     
    return post;
    }
}
