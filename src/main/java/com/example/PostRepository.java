package com.example;

import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Integer> {

    Post findByMember(Member member);
    Post findById (Integer id);
}
