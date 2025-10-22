package com.fabianospdev.volunteer.repositories;


import com.fabianospdev.volunteer.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<UserModel, String>, UserRepositoryExtension {
}