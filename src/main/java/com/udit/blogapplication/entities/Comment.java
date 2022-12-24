package com.udit.blogapplication.entities;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String name;
	
	private String email;
	
	private String comment;
	
//	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
//	@JoinTable(name = "post_comment",
//	  joinColumns = @JoinColumn(name="comment_id",updatable = false),
//	  inverseJoinColumns = @JoinColumn(name="post_id",updatable = false)
//			)
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
	@JoinColumn(name="post_id")
	private Post posts;
	
	@Column(name="created_at",nullable = false,updatable = false)
	@CreationTimestamp
	private Timestamp createdDate;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private Timestamp updatedDate;
	
	

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, String name, String email, String comment) {
		super();
		
		this.name = name;
		this.email = email;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	

//	public List<Post> getPosts() {
//		return posts;
//	}
//
//	public void setPosts(List<Post> posts) {
//		this.posts = posts;
//	}
	
	

	@Override
	public String toString() {
		return "Comment [id=" + id + ", name=" + name + ", email=" + email + ", comment=" + comment + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + "]";
	}

	public Post getPosts() {
		return posts;
	}

	public void setPosts(Post posts) {
		this.posts = posts;
	}

		
	
	
	
}
