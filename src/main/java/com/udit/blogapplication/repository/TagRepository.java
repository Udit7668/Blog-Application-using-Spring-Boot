package com.udit.blogapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.udit.blogapplication.entities.Tag;

public interface TagRepository extends CrudRepository<Tag,Integer>{
    
    @Query("select u from Tag u where name=:c")
     public List<Tag> getAllPostByTag(@Param("c") String tags);
}
