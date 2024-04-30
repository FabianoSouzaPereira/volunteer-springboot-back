package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.dto.UserDTO;
import com.fabianospdev.volunteer.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>, QueryByExampleExecutor<User>{
}