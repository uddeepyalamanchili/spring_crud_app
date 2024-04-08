package com.example.spring_crud_app;

import org.springframework.data.repository.CrudRepository;

import com.example.spring_crud_app.Testing;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TestingRepository extends CrudRepository<Testing, Integer> {

}