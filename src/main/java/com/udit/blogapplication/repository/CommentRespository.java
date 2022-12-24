package com.udit.blogapplication.repository;

import org.springframework.data.repository.CrudRepository;

import com.udit.blogapplication.entities.Comment;

public interface CommentRespository extends CrudRepository<Comment,Integer>{
    
    public Comment findCommentById(Integer id);
}
