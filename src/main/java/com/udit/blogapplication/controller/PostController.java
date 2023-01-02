package com.udit.blogapplication.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.udit.blogapplication.entities.Comment;
import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.entities.Tag;
import com.udit.blogapplication.services.CommentService;
import com.udit.blogapplication.services.PostService;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/newPost")
    public String addPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "new-post";
    }

    @GetMapping("/process")
    public String processForm(@ModelAttribute("post") Post post, Model model, @ModelAttribute("tags") Tag tags) {
        System.out.println(post);
        System.out.println(tags);
        this.postService.addPost(post);
        this.postService.addTags(post, tags);
        List<Post> posts = this.postService.getAllPost();
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showAllPost(Model model, Authentication authentication) {
        return findPaginated(1, model);
    }

    @GetMapping("/delete/{viewId}")
    public String deletePost(@PathVariable(value = "viewId") Integer id) {

        this.postService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/view/{viewId}")
    public String viewPost(@PathVariable("viewId") Integer id, Model model, Authentication authentication) {
        Post post = this.postService.getPostById(id);
        model.addAttribute("post", post);
        Comment comment = new Comment();
        String name = authentication.getName();
        model.addAttribute("name", name);
        model.addAttribute("comment", comment);
        return "view-post";
    }

    @GetMapping("/comment/{viewId}")
    public String addComment(@PathVariable("viewId") Integer id, Model model,
            @ModelAttribute("comments") Comment comment) {
        Post post = this.postService.getPostById(id);
        model.addAttribute("post", post);
        this.postService.addComment(comment, id);
        Comment commentNew = new Comment();
        model.addAttribute("comment", commentNew);
        return "view-post";
    }

    @GetMapping("/update/{viewId}")
    public String updatePost(@PathVariable("viewId") Integer id, Model model) {
        Post post = this.postService.getPostById(id);
        List<Tag> tags = post.getTags();
        model.addAttribute("tags", tags);
        model.addAttribute("comments", "comments");
        model.addAttribute("post", post);
        return "update-post";
    }

    @GetMapping("/updatepost")
    public String updatePost(@ModelAttribute("post") Post post, Model model, @ModelAttribute("tags") Tag tags) {
        this.postService.addPost(post);
        this.postService.addTags(post, tags);
        return "redirect:/";
    }

    @GetMapping("/deleteComment/{deleteId}")
    public String deleteComment(@PathVariable("deleteId") Integer deleteId, Model model,
            @RequestParam("postId") Integer postId) {
        model.addAttribute("viewId", deleteId);

        this.postService.deleteComment(deleteId, postId);
        return "redirect:/view/" + postId;
    }

    @GetMapping("/updateComment/{updateId}")
    public String updateComment(@PathVariable("updateId") Integer commentId, @RequestParam("postId") Integer postId,
            Model model) {
        Comment comment = this.commentService.getCommentById(commentId);

        this.postService.deleteComment(commentId, postId);
        model.addAttribute("comment", comment);
        Post post = this.postService.getPostById(postId);
        model.addAttribute("post", post);
        return "view-post";
    }
    @GetMapping("/sortByDate")
     public String sortPostByDatePagination(@RequestParam("sortby") String sortBy,Model model){
     return sortPostsByDate(1,sortBy,model);
     }

    @GetMapping("/sortByDate/{pageNumber}")
    public String sortPostsByDate(
     @PathVariable("pageNumber") Integer pageNumber,    
      @RequestParam("sortby") String sortBy, Model model) {
   int pageSize=10;
       Page<Post> page=this.postService.sortPostByDate(sortBy, pageNumber, pageSize);
       List<Post> posts=page.getContent();
        model.addAttribute("posts", posts);
    
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        Set<String> listOfAuthors = this.postService.findAllAuthors();
        Set<String> lisOfTags = this.postService.findAllTags();
        model.addAttribute("authors", listOfAuthors);

        model.addAttribute("listOfTags", lisOfTags);
        return "post-confirmation";
    }

    @GetMapping("/sort")
    public String sortPosts(@RequestParam("sortby") String sortBy, Model model, @RequestParam("postId") String postId) {

        System.out.println(postId);
        List<Post> posts = this.postService.sortPost(sortBy, postId);
        model.addAttribute("posts", posts);

        String postid = "";
        for (Post post : posts) {
            postid = postid + String.valueOf(post.getId()) + ",";
        }
        model.addAttribute("postId", postid);
        
        Set<String> listOfAuthors = this.postService.findAllAuthors();
        Set<String> lisOfTags = this.postService.findAllTags();
        model.addAttribute("authors", listOfAuthors);

        model.addAttribute("listOfTags", lisOfTags);
        return "search-dashboard";
    }

    @GetMapping("/search")
    public String searchPostsPagination(
            @RequestParam("title") String searchBy, Model model) {

        return searchPosts(1, searchBy, model);
    }

    @GetMapping("/search/{pageNo}")
    public String searchPosts(
            @PathVariable("pageNo") Integer pageNo,
            @RequestParam("title") String searchBy, Model model) {
        int pageSize = 10;
        Page<Post> page = this.postService.getAllPostBySearch(searchBy, pageNo, pageSize);
        List<Post> posts = page.getContent();
        model.addAttribute("searchBy", searchBy);
        Set<String> listOfAuthors = new HashSet<>();
        for (Post post : posts) {
            listOfAuthors.add(post.getAuthor());
        }
        Set<String> lisOfTags = this.postService.getAllTagsByAuthorsAndPost(posts, listOfAuthors);
        model.addAttribute("authors", listOfAuthors);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("listOfTags", lisOfTags);
        model.addAttribute("posts", posts);
        return "search-dashboard";
    }

    @GetMapping("/filter/search")
    public String filterAndSearch(@RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "Date", required = false) List<String> date,
            @RequestParam(value = "tag", required = false) List<String> tags,
            Model model) throws ParseException {
        List<String> authors = new ArrayList<>();
        authors.add(author);
        Set<Post> posts = this.postService.getAllPostByFilter(authors, tags, date);
        String postId = "";
        for (Post post : posts) {
            postId = postId + String.valueOf(post.getId()) + ",";
        }

        model.addAttribute("postId", postId);
        model.addAttribute("posts", posts);
        Set<String> lisOfTags = this.postService.findAllTags();
        model.addAttribute("listOfTags", lisOfTags);
        model.addAttribute("searchBy", author);
        return "search-dashboard";

    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "author", required = false) List<String> authors,
            @RequestParam(value = "Date", required = false) List<String> date,
            @RequestParam(value = "tag", required = false) List<String> tags,
            Model model) throws ParseException {

        Set<Post> posts = this.postService.getAllPostByFilter(authors, tags, date);

        String postId = "";
        for (Post post : posts) {
            postId = postId + String.valueOf(post.getId()) + ",";
        }
        model.addAttribute("postId", postId);
        model.addAttribute("posts", posts);
        Set<String> listOfAuthors = this.postService.findAllAuthors();
        Set<String> lisOfTags = this.postService.findAllTags();
        model.addAttribute("authors", listOfAuthors);
        model.addAttribute("listOfTags", lisOfTags);
        return "post-confirmation";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = 10;
        Page<Post> page = this.postService.findPaginated(pageNo, pageSize);
        List<Post> listOfPosts = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        Set<String> listOfAuthors = this.postService.findAllAuthors();
        Set<String> lisOfTags = this.postService.findAllTags();
        model.addAttribute("authors", listOfAuthors);

        model.addAttribute("listOfTags", lisOfTags);
        model.addAttribute("posts", listOfPosts);
        return "post-confirmation";
    }

}
