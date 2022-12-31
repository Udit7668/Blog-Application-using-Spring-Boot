package com.udit.blogapplication.services;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

   public void addPost(Post post) {
      this.postRepository.save(post);
   }

   public void addTags(Post post, Tag tags) {
      post.addTag(tags);
      this.tagRepository.save(tags);
   }

   public List<Post> getAllPost() {
      List<Post> posts = (List<Post>) this.postRepository.findAll();
      return posts;
   }

   public void deletePost(Integer id) {
      Post post = this.postRepository.findPostById(id);
      this.postRepository.delete(post);
   }

   public Post getPostById(Integer id) {
      Post post = this.postRepository.findPostById(id);
      return post;
   }

   public void addComment(Comment comment, Integer id) {
      Post post = this.postRepository.findPostById(id);
      post.addComment(comment);
      comment.setPosts(post);
      this.commentRespository.save(comment);
   }

   public void deleteComment(Integer deleteId, Integer postId) {
      Comment comment = this.commentRespository.findCommentById(deleteId);
      Post post = this.postRepository.findPostById(postId);
      post.getComments().remove(comment);
      this.commentRespository.delete(comment);
   }

   public List<Post> sortPost(String sortBy,String postId) {
      if(!postId.isBlank()){
      String id[]=postId.split(",");
      List<Integer> listOfPostId=new ArrayList<>();
      for(String singleId:id){
      listOfPostId.add(Integer.parseInt(singleId));
      }
      List<Post> listOfPosts=new ArrayList<>();
      List<Post> posts;
      if (sortBy.equalsIgnoreCase("asc")) {
         posts = this.postRepository.getAllPostOrderAsc(sortBy);
      } else {
         posts = this.postRepository.getAllPostOrderDesc(sortBy);
      }
      for(Post post:posts){
   if(listOfPostId.contains(post.getId())){
      listOfPosts.add(post);
   }
      }
 return listOfPosts;

   }
     List<Post> posts;
      if (sortBy.equalsIgnoreCase("asc")) {
         posts = this.postRepository.getAllPostOrderAsc(sortBy);
      } else {
         posts = this.postRepository.getAllPostOrderDesc(sortBy);
      }


      return posts;

   }

   public List<Post> getAllPostByTitle(String searchBy) {
      List<Post> posts = new ArrayList<>();
      List<Tag> tags = this.tagRepository.getAllTagByTag(searchBy);
      System.out.println(tags);
      if (!tags.isEmpty()) {
         for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(searchBy)) {
               List<Post> listOfPost = tag.getPosts();
               for (Post post : listOfPost) {
                  posts.add(post);
               }
            }
         }
         return posts;
      }
      posts = this.postRepository.getAllPostByTitle(searchBy);
      return posts;
   }
   public Set<Post> getAllPostByFilter(List<String> authors, List<String> tags, List<String> date)
         throws ParseException {
      Set<Post> posts = new HashSet<>();

      // case 1 --when ony authors is present
      if (authors != null && tags == null && date.get(0).isBlank() && date.get(1).isBlank()) {
         List<Post> listOfPosts=this.postRepository.getAllPostByAuthor(authors);
         posts=new HashSet<>(listOfPosts);
         return posts;
      }

      // case 2 when only tags is present
      if (authors == null && tags != null && date.get(0).isBlank() && date.get(1).isBlank()) {
         List<Post> lisOfPosts=this.postRepository.getAllPostByTag(tags);
         posts=new HashSet<>(lisOfPosts);
         return posts;
      }

      // case 3--when both tag and author is non empty
      if (authors != null && tags != null && date.get(0).isBlank() && date.get(1).isBlank()) {
         for (String tag : tags) {
            for (String author : authors) {
               List<Post> listOfPosts = this.postRepository.getAllPostByAuthorAndTag(author, tag);
               for (Post post : listOfPosts) {
                  posts.add(post);
               }
            }
         }
         return posts;
      }

      // case 4--when only date is given
      if (authors == null && tags == null && !date.get(0).isBlank() && !date.get(1).isBlank()) {
         String[] str = date.get(0).split("-");
         LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
               Integer.parseInt(str[2]), 0, 0, 0);
         Date start = convertLocalDateTimeToDateUsingTimestamp(startDate);
         String[] str1 = date.get(1).split("-");
         LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]),
               Integer.parseInt(str1[2]), 0, 0, 0);
         Date end = convertLocalDateTimeToDateUsingTimestamp(endDate);
         List<Post> list = this.postRepository.findByCreationDateBetween(start, end);
         for (Post singlePost : list) {
            posts.add(singlePost);
         }
         return posts;
      }

      // case 5--when author and date is given
      if (authors != null && tags == null && !date.get(0).isBlank() && !date.get(1).isBlank()) {
         String[] str = date.get(0).split("-");
         LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
               Integer.parseInt(str[2]), 0, 0, 0);
         Date start = convertLocalDateTimeToDateUsingTimestamp(startDate);
         String[] str1 = date.get(1).split("-");
         LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]),
               Integer.parseInt(str1[2]), 0, 0, 0);
         Date end = convertLocalDateTimeToDateUsingTimestamp(endDate);
         List<Post> listOfPosts=this.postRepository.getAllPostByAuthorAndCreationDateBetween(authors, start, end);
         posts=new HashSet<>(listOfPosts);
         return posts;
      }

      // case 6--when tags and date is given
      if (authors == null && tags != null && !date.get(0).isBlank() && !date.get(1).isBlank()) {
         String[] str = date.get(0).split("-");
         LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
               Integer.parseInt(str[2]), 0, 0, 0);
         Date start = convertLocalDateTimeToDateUsingTimestamp(startDate);
         String[] str1 = date.get(1).split("-");
         LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]),
               Integer.parseInt(str1[2]), 0, 0, 0);
         Date end = convertLocalDateTimeToDateUsingTimestamp(endDate);
         List<Post> listOfPosts=this.postRepository.getAllPostByTagAndCreationDateBetween(tags, start, end);
         posts=new HashSet<>(listOfPosts);
         return posts;
      }

      // when all three are given
      if (authors != null && tags != null && !date.get(0).isBlank() && !date.get(1).isBlank()) {
         String[] str = date.get(0).split("-");
         LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
               Integer.parseInt(str[2]), 0, 0, 0);
         Date start = convertLocalDateTimeToDateUsingTimestamp(startDate);
         String[] str1 = date.get(1).split("-");
         LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]),
               Integer.parseInt(str1[2]), 0, 0, 0);
         Date end = convertLocalDateTimeToDateUsingTimestamp(endDate);
       List<Post> listOfPosts=this.postRepository.getAllPostByTagAndCreationDateBetween(tags, start, end);
   for(Post post:listOfPosts){
      if(authors.contains(post.getAuthor())){
         posts.add(post);
      }
   }
         return posts;
      }

      return null;
   }

   public Date convertLocalDateTimeToDateUsingTimestamp(LocalDateTime dateToConvert) {
      return java.sql.Timestamp.valueOf(dateToConvert);
   }

   public Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
      Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
            : Sort.by(sortField).descending();
      Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      return this.postRepository.findAll(pageable);
   }

   public Set<String> findAllAuthors() {
      Set<String> listOfAuthors = new HashSet<>();
      List<Post> listOfPost = (List<Post>) this.postRepository.findAll();
      for (Post post : listOfPost) {
         listOfAuthors.add(post.getAuthor());
      }
      return listOfAuthors;
   }

   public Set<String> findAllTags() {
      Set<String> listOfTags = new HashSet<>();
      List<Tag> tags = (List<Tag>) this.tagRepository.findAll();
      for (Tag tag : tags) {
         listOfTags.add(tag.getName());
      }

      return listOfTags;
   }



   public List<String> getAllAuthorByAuthorAndTag(String searchBy){
    List<String> listOfAuthors=this.postRepository.getAllAuthorByAuthorAndTag(searchBy);
      return listOfAuthors;
   }


   public Set<String> getAllAuthorsByPost(List<Post> posts){
    Set<String> authors=new HashSet<>();
    for(Post post:posts){
      authors.add(post.getAuthor());
    }
      return authors;
   }


   public Set<String> getAllTagsByAuthorsAndPost(List<Post> posts,Set<String> authors){
    Set<String> tags=new HashSet<>();
    for(Post post:posts){
      if(authors.contains(post.getAuthor())){
        List<Tag> listOfTags=post.getTags();
     for(Tag tag:listOfTags){
       tags.add(tag.getName());
     }

      }
    }

      return tags;
   }

}
