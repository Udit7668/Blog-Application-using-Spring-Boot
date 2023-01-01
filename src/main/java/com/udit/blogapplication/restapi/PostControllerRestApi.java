package com.udit.blogapplication.restapi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.services.PostService;

@RestController
@RequestMapping("/getPosts")
public class PostControllerRestApi {

    @Autowired
    private PostService postService;
    
    @GetMapping("byId/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id){ 
    Post post=this.postService.getPostById(id);    
    if(post==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    return ResponseEntity.of(Optional.of(post));
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPost(){
        List<Post> posts=this.postService.getAllPost();
        if(posts.size()<=0){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(posts));
    }

  @PostMapping("/add")
   public Post addPost(@RequestBody Post post){
    System.out.println(post);
       this.postService.addPost(post);
       return post;
   }

 @GetMapping("/sortBy/{sort}")
   public List<Post> sortPostByDate(@PathVariable("sort") String sortBy){
   List<Post> listOfPosts=this.postService.getAllPost();
   String listOfIds="";
   for(Post post:listOfPosts){
   listOfIds=listOfIds+ String.valueOf(post.getId())+",";
 }
  List<Post> posts=this.postService.sortPost(sortBy, listOfIds);
    return posts;
   }

@GetMapping("/search/{searchBy}")
   public List<Post> getAllPostBySearch(@PathVariable("searchBy") String searchBy){
List<Post> posts=this.postService.getAllPostByTitle(searchBy);
    return posts;
   }


   @GetMapping("/filter/{authors}/{tags}/{date}")
   public List<Post> getAllPostByFilter(@PathVariable(value = "authors",required=false) String author,
   @PathVariable(value = "tags",required=false) String tag,
   @PathVariable(value = "date",required=false) String dates) throws ParseException{
  
    List<String> authors=convert(author);
    List<String> tags=convert(tag);
    List<String> date=convert(dates);
    Set<Post> posts = this.postService.getAllPostByFilter(authors, tags,date);
    List<Post> listOfPosts=new ArrayList<>(posts);
    return listOfPosts;
   }


   public List<String> convert(String string){
    String[] list=string.split(",");
    List<String> listOf=new ArrayList<>();
    for(String single:list){
      listOf.add(single);
    }
    return listOf;
   }


   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Void> deletePostById(@PathVariable("id") Integer id){
    try{
      this.postService.deletePost(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    catch(Exception e){
      e.printStackTrace();
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

   }

   @PutMapping("/update/{id}")
   public ResponseEntity<Post> updatePost(@PathVariable("id") Integer id,@RequestBody Post post){

     return null;
   }
}





