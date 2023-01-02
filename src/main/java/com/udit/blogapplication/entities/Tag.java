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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name="tags")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	private String name;
	
	@Column(name="created_at")
	@CreationTimestamp
	private Timestamp creationTime;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private Timestamp updateTime;

	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.EAGER)
	@JoinTable(name = "post_tag",
	  joinColumns = @JoinColumn(name="tag_id"),
	  inverseJoinColumns =@JoinColumn(name="post_id")
			)
			@JsonBackReference
	private List<Post> posts;
	
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}
	
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Tag() {
		super();
	}

	public Tag(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", creationTime=" + creationTime + ", updateTime=" + updateTime
				+ "]";
	}


	public void addPost(Post post){
		if(posts==null){
			posts=new ArrayList<>();
		}
		posts.add(post);
	}
	
	
	
}
