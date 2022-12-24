package com.udit.blogapplication.repository;

import org.springframework.data.repository.CrudRepository;


import com.udit.blogapplication.entities.Tag;

public interface TagRepository extends CrudRepository<Tag,Integer>{
    
}
