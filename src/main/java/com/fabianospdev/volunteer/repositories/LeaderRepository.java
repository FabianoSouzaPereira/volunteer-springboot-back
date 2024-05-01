package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.Leader;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderRepository extends MongoRepository<Leader, String>, LeaderRepositoryExtension {
}