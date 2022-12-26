package com.udit.blogapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.entities.Tag;

public interface PostRepository extends CrudRepository<Post,Integer> {
    

    public Post findPostById(Integer id);

     @Query("select u FROM Post  u order by u.creationDate desc")
     public List<Post> getAllPostOrderDesc(@Param("n") String sortBy);
    
     @Query("select u FROM Post  u order by u.creationDate asc")
     public List<Post> getAllPostOrderAsc(@Param("n") String sortBy);

     @Query("select u FROM Post u where u.title=:c or u.author=:c or u.content=:c")
     public List<Post> getAllPostByTitle(@Param("c") String searchBy);

     @Query("select u FROM Post u where u.author=:c")
     public List<Post> getAllPostByAuthor(@Param("c") String filterBy);
     
}
