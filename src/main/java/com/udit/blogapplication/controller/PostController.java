package com.udit.blogapplication.controller;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udit.blogapplication.entities.Comment;
import com.udit.blogapplication.entities.Post;
import com.udit.blogapplication.entities.Tag;
import com.udit.blogapplication.services.PostService;

@Controller
public class PostController {
    @Autowired
   private PostService postService;
    @GetMapping("/newPost")
    public String addPost(Model model){
     Post post=new Post();
     model.addAttribute("post", post);
        return "new-post";
    }
   @GetMapping("/process")
    public String processForm(@ModelAttribute("post") Post post,Model model,@ModelAttribute("tags") Tag tags){
    System.out.println(post);
    System.out.println(tags);
    this.postService.addPost(post);
    this.postService.addTags(post, tags);
    List<Post> posts=this.postService.getAllPost();
    model.addAttribute("posts", posts);
    model.addAttribute("tags", tags);
   return "redirect:/";
    }

    @GetMapping("/")
	public String showAllPost(Model model) {
	    List<Post> posts= this.postService.getAllPost();
	    model.addAttribute("posts",posts);
		return "post-confirmation";
	}

    @GetMapping("/delete/{viewId}")
	public String deletePost(@PathVariable(value="viewId") Integer id) {
    System.out.println(id+"................................");
		this.postService.deletePost(id);
		return "redirect:/";
	}

    @GetMapping("/view/{viewId}")
	public String viewPost(@PathVariable("viewId") Integer id,Model model){
		Post post=this.postService.getPostById(id);
        System.out.println("...........................");
        System.out.println(post);
        System.out.println("..........................");
		model.addAttribute("post",post);
		Comment comment=new Comment();
		model.addAttribute("comment",comment);
	   return "view-post";
	}


    @GetMapping("/comment/{viewId}")
	public String addComment(@PathVariable("viewId") Integer id,Model model,@ModelAttribute("comments") Comment comment){
      Post post=  this.postService.getPostById(id);
      model.addAttribute("post",post);
		this.postService.addComment(comment, id);
        model.addAttribute("comment",comment);
	   return "view-post";
	}

	

    @GetMapping("/update/{viewId}")
	public String updatePost(@PathVariable("viewId") Integer id,Model model) {
      Post post=this.postService.getPostById(id);
      Tag tags=new Tag();
      System.out.println(id+".................");
    
      model.addAttribute("tags",tags);
      model.addAttribute("comments","comments");
      model.addAttribute("post",post);
		return "update-post";
	}


    @GetMapping("/deleteComment/{deleteId}")
	public String deleteComment(@PathVariable("deleteId") Integer deleteId,Model model,@RequestParam("postId") Integer postId) {
	   model.addAttribute("viewId",deleteId);
       System.out.println(deleteId+"__________________________________");
       System.out.println(postId+"...................*****");
       this.postService.deleteComment(deleteId,postId);
		return "redirect:/view/"+postId;
	}
	


}
