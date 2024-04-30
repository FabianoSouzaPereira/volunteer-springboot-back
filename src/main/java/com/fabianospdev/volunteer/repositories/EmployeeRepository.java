package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<User, String>{
}