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

     @Query("select u FROM Post  u order by u.creationDate desc")
     public Page<Post> getAllPostOrderDesc(@Param("n") String sortBy,Pageable pageable);
    
     @Query("select u FROM Post  u order by u.creationDate asc")
     public List<Post> getAllPostOrderAsc(@Param("n") String sortBy);

     @Query("select u FROM Post  u order by u.creationDate asc")
     public Page<Post> getAllPostOrderAsc(@Param("n") String sortBy,Pageable pageable);

     @Query("select u FROM Post u where u.title=:c or u.author=:c or u.content=:c")
     public List<Post> getAllPostByTitle(@Param("c") String searchBy);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where t.name=:x or u.title=:x or u.author=:x or u.content=:x",nativeQuery = true)
     public List<Post> getAllPostByTitleOrTag( @Param("x") String searchBy);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where t.name=:x or u.title=:x or u.author=:x or u.content=:x",nativeQuery = true)
     public Page<Post> getAllPostByTitleOrTag(@Param("x") String searchBy,Pageable pageable);

     @Query("select u FROM Post u where u.author=:c")
     public List<Post> getAllPostByAuthor(@Param("c") String filterBy);

     @Query("select u FROM Post u where u.author in :c")
     public List<Post> getAllPostByAuthor(@Param("c")  List<String> authors);

     @Query(value="select * FROM post u where u.author in :c and u.created_at between :s and :e",nativeQuery = true)
     public List<Post> getAllPostByAuthorAndCreationDateBetween(@Param("c")  List<String> authors,@Param("s") Date startDate,@Param("e") Date endDate);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where t.name in :x",nativeQuery = true)
     public List<Post> getAllPostByTag( @Param("x") List<String> tags);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where t.name in :x",nativeQuery = true)
     public Page<Post> getAllPostByTag( @Param("x") List<String> tags,Pageable pageable);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where t.name in :x and u.created_at between :s and :e",nativeQuery = true)
     public List<Post> getAllPostByTagAndCreationDateBetween(@Param("x") List<String> tags,@Param("s") Date startDate,@Param("e") Date endDate);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where t.name in :x and u.created_at between :s and :e",nativeQuery = true)
     public Page<Post> getAllPostByTagAndCreationDateBetween(@Param("x") List<String> tags,@Param("s") Date startDate,@Param("e") Date endDate,Pageable pageable);

     @Query(value = "select u.* from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where u.author=:c and t.name=:x",nativeQuery = true)
     public List<Post> getAllPostByAuthorAndTag(@Param("c") String author,@Param("x") String tag);

     public List<Post> findByCreationDateBetween(Date startDate,Date endDate);

     public Page<Post> findByCreationDateBetween(Date startDate,Date endDate,Pageable pageable);

     @Query(value = "select u.author from post u join post_tag pt on u.id=pt.post_id join tags t on t.id=pt.tag_id  where u.author=:c or t.name=:c or u.title:c or u.author=:c or u.content:c",nativeQuery = true)
     public List<String> getAllAuthorByAuthorAndTag(@Param("c") String author
     );

     @Query(value="select * FROM post u where u.id in :c order by u.created_at asc",nativeQuery = true)
    public List<Post> getAllPostByIdAndOrderAsc(@Param("c") List<Integer> posstId);

    @Query(value="select * FROM post u where u.id in :c order by u.created_at desc",nativeQuery = true)
    public List<Post> getAllPostByIdAndOrderDesc(@Param("c") List<Integer> posstId);


    @Query(value="select * FROM post u where u.author in :c",nativeQuery = true)
    public Page<Post> getAllPostByAuthorFilter(@Param("c")  List<String> authors,Pageable pageable);


    public Page<Post> findAll(Pageable pageable);
     
}
