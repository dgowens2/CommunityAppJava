package com.example;

import org.springframework.data.repository.CrudRepository;


public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

    Organization findByName(String name);
    Organization findById(int id);

}
