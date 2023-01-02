package com.udit.blogapplication.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name="post")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Post {
	
	@Id
   	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String title;
	
	private String excrept;
	
	private String content;
	
	private String author;
	
	@Column(name="is_published")
	private String isPublished="No";
	
	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	@Column(name="created_at",nullable = false,updatable = false)
	@CreationTimestamp
	private Timestamp creationDate;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private Timestamp updateDate;
	 
	
	
	@OneToMany(mappedBy ="posts",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Comment> comments;
	

	@ManyToMany(cascade =CascadeType.ALL)
	@JoinTable(name = "post_tag",
	  joinColumns = @JoinColumn(name="post_id"),
	  inverseJoinColumns =@JoinColumn(name="tag_id")
			)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Tag> tags;

	
	public Post(String title,String excrept, String content, String author, Timestamp publishedDate,
			Timestamp updateDate) {
		super();
		this.title=title;
		this.excrept = excrept;
		this.content = content;
		this.author = author;
	}

	public Post() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcrept() {
		return excrept;
	}

	public void setExcrept(String excrept) {
		this.excrept = excrept;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	

	
	public Timestamp getCreationDate() {
		return creationDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Post [Id=" + id + ", title=" + title + ", excrept=" + excrept + ", content=" + content + ", author="
				+ author + ", publishedDate=" + creationDate + ", updateDate=" + updateDate + "]";
	}
	
	public void addComment(Comment comment) {
		if(comments==null) {
			comments=new ArrayList<Comment>();
		}
		comments.add(comment);
	}
	
	public void addTag(Tag tag) {
		if(tags==null) {
			tags=new ArrayList<Tag>();
		}
		tags.add(tag);
	}
	
	
	
	
	
	
}
