package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface PostRepository extends CrudRepository<Post, Integer> {

    ArrayList<Post> findByMember(Member member);
    Post findById (Integer id);
}
