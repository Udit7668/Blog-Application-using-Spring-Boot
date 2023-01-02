package com.udit.blogapplication.restapi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<Post>> getAllPost(
   @RequestParam(value="pageNumber",defaultValue = "1",required = false) Integer pageNumber,
   @RequestParam(value="pageSize",defaultValue = "5",required = false) Integer pageSize

    ){
        List<Post> posts=this.postService.findAllPost(pageNumber, pageSize);
        if(posts.size()<=0){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(posts));
    }

  @PostMapping("/add")
   public ResponseEntity<Post> addPost(@RequestBody Post post){
         try{
        this.postService.addPost(post);
        return ResponseEntity.of(Optional.of(post));
         }
         catch(Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }

   }

 @GetMapping("/sortBy/{sort}")
   public List<Post> sortPostByDate(@PathVariable("sort") String sortBy,
   @RequestParam(value="pageNumber",defaultValue = "1",required = false) Integer pageNumber,
   @RequestParam(value="pageSize",defaultValue = "5",required = false) Integer pageSize  
   ){
     List<Post> posts=this.postService.sortPost(sortBy,pageNumber,pageSize);
     return posts;
   }

@GetMapping("/search/{searchBy}")
   public List<Post> getAllPostBySearch(@PathVariable("searchBy") String searchBy,
   @RequestParam(value="pageNumber",defaultValue = "1",required = false) Integer pageNumber,
   @RequestParam(value="pageSize",defaultValue = "5",required = false) Integer pageSize  
   ){
    List<Post> posts=this.postService.getAllPostByTitle(searchBy,pageNumber,pageSize);
    return posts;
   }

   @GetMapping("/filterByAuthors/{authors}")
   public ResponseEntity<List<Post>> getAllPostByFilterAuthor(@PathVariable(value = "authors",required=false) String author) throws ParseException{
    List<String> authors=convert(author);
    List<Post> posts=this.postService.getAllPostByAuthorFilter(authors);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
   }


   @GetMapping("/filterByTags/{tags}")
   public ResponseEntity<List<Post>> getAllPostByFilteringTag(@PathVariable(value = "tags",required=false) String tag) throws ParseException{
    List<String> tags=convert(tag);
    List<Post> posts=this.postService.getAllPostByTags(tags);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
   }

   @GetMapping("/filterByDate/{date}")
   public ResponseEntity<List<Post>> getAllPostByFilteringDateBetween(@PathVariable(value = "date",required=false) String date) throws ParseException{
    List<String> dates=convert(date);
    List<Post> posts=this.postService.getAllPostByDate(dates);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
   }

   @GetMapping("/filter/{authors}/{tags}/{date}")
   public ResponseEntity<List<Post>> getAllPostByFilterAuthorAndTagsAndDateBetween(@PathVariable(value = "authors",required=false) String author,
   @PathVariable(value = "tags",required=false) String tag,
   @PathVariable(value = "date",required=false) String date  ) throws ParseException{
    List<String> authors=convert(author);
    List<String> tags=convert(tag);
    List<String> dates=convert(date);
    List<Post> posts=this.postService.getAllPostByFilterAll(authors, tags, dates);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
   }

   @GetMapping("/filterByAuthorAndTag/{authors}/{tags}")
   public ResponseEntity<List<Post>> getAllPostByFilterAuthorAndTags(@PathVariable(value = "authors",required=false) String author,
   @PathVariable(value = "tags",required=false) String tag) throws ParseException{
    List<String> authors=convert(author);
    List<String> tags=convert(tag);
    List<Post> posts=this.postService.getAllPostByAuthorAndTags(authors, tags);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
   }

   @GetMapping("/filterByAuthorAndDate/{authors}/{date}")
   public ResponseEntity<List<Post>> getAllPostByFilterAuthorAndDateBetween(@PathVariable(value = "authors",required=false) String author,
   @PathVariable(value = "date",required=false) String date  ) throws ParseException{
    List<String> authors=convert(author);
    List<String> dates=convert(date);
    List<Post> posts=this.postService.getAllPostByAuthorsAndDateBetween(authors, dates);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
   }


   @GetMapping("/filterByTagsAndDate/{tags}/{date}")
   public ResponseEntity<List<Post>> getAllPostByFilterByTagsAndDateBetween(@PathVariable(value = "tags",required=false) String tag,
   @PathVariable(value = "date",required=false) String date  ) throws ParseException{
    List<String> tags=convert(tag);
    List<String> dates=convert(date);
    List<Post> posts=this.postService.getAllPostByTagsAndDateBetween(tags, dates);
    if(posts==null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.of(Optional.of(posts));
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
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

   }

   @PutMapping("/update")
   public ResponseEntity<Post> updatePost(@RequestBody Post post){
    try{
      this.postService.addPost(post);
      return ResponseEntity.of(Optional.of(post));
       }
       catch(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
}
}





