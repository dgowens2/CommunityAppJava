package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface PostRepository extends CrudRepository<Post, Integer> {
    Post findById (Integer id);
    ArrayList<Post> findByAuthor(Member author);

    ArrayList<Post> findByAuthorOrderByDateAsc (Member author);

    ArrayList<Post> findByOrganization(Organization organization);
    ArrayList<Post> findByOrganizationOrderByDateAsc(Organization organization);
}
