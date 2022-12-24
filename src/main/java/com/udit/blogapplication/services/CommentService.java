package com.udit.blogapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udit.blogapplication.entities.Comment;
import com.udit.blogapplication.repository.CommentRespository;

@Component
public class CommentService {
    @Autowired
    private CommentRespository commentRespository;

    public Comment getCommentById(Integer id){
   Comment comment=this.commentRespository.findCommentById(id);
   return comment;

}
}
