package com.udit.blogapplication.services;

import java.util.List;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udit.blogapplication.entities.Comment;
import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.entities.Tag;
import com.udit.blogapplication.repository.CommentRespository;
import com.udit.blogapplication.repository.PostRepository;
import com.udit.blogapplication.repository.TagRepository;

@Component
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;
  
    @Autowired
    private CommentRespository commentRespository;

     public void addPost(Post post){
        this.postRepository.save(post);
     }

     public void addTags(Post post,Tag tags){
        post.addTag(tags);
        this.tagRepository.save(tags);
        }
     

    public List<Post> getAllPost(){
     List<Post> posts=   (List<Post>) this.postRepository.findAll();
     return posts;
     }


    public void deletePost(Integer id){
        Post post=this.postRepository.findPostById(id);
        this.postRepository.delete(post);
    }

    public Post getPostById(Integer id){
   Post post=this.postRepository.findPostById(id);
   return post;
    }

    public void addComment(Comment comment,Integer id){
        Post post=this.postRepository.findPostById(id);
       post.addComment(comment);
       comment.setPosts(post);
       this.commentRespository.save(comment);
    }


    public void deleteComment(Integer deleteId,Integer postId){
       Comment comment= this.commentRespository.findCommentById(deleteId);
       Post post=this.postRepository.findPostById(postId);
       post.getComments().remove(comment);
       this.commentRespository.delete(comment);
    }


    public List<Post> sortPost(String sortBy){
      List<Post> posts;
      if(sortBy.equalsIgnoreCase("asc")){
        posts=this.postRepository.getAllPostOrderAsc(sortBy);
      }
      else{
     posts= this.postRepository.getAllPostOrderDesc(sortBy);
      }
     return posts;

     }


     public List<Post> getAllPostByTitle(String searchBy){
      List<Post> posts;
      posts=this.postRepository.getAllPostByTitle(searchBy);
      return posts;
     }

}
