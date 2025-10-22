package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.PartnerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends MongoRepository<PartnerModel, String>, PartnerRepositoryExtension {
}