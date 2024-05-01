package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.Pastor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastorRepository extends MongoRepository<Pastor, String>, PastorRepositoryExtension {
}