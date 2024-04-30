package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.Secretary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretaryRepository extends MongoRepository<Secretary, String> {
}