package com.udit.blogapplication.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.udit.blogapplication.entities.Post;

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

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where u.author=:c and t.name=:x",nativeQuery = true)
     public List<Post> getAllPostByAuthorAndTag(@Param("c") String author,
     @Param("x") String tag
     );

     public List<Post> findByCreationDateBetween(Date startDate,Date endDate);


     @Query(value = "select u.author from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where u.author=:c or t.name=:c or u.title:c or u.author=:c or u.content:c",nativeQuery = true)
     public List<String> getAllAuthorByAuthorAndTag(@Param("c") String author
     );

    public Page<Post> findAll(Pageable pageable);
     
}
