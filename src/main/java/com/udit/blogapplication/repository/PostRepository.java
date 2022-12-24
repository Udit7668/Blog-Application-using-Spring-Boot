package com.udit.blogapplication.repository;

import org.springframework.data.repository.CrudRepository;

import com.udit.blogapplication.entities.Post;

public interface PostRepository extends CrudRepository<Post,Integer> {
    

    public Post findPostById(Integer id);
}
