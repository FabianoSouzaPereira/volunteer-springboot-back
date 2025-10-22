package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.LeaderModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderRepository extends MongoRepository<LeaderModel, String>, LeaderRepositoryExtension {
}