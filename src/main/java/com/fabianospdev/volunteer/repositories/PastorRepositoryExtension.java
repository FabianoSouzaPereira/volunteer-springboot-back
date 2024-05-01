package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.dto.PastorDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PastorRepositoryExtension{

    @Query(value = "{}", fields = "{ 'id' : 1, 'name' : 1, 'phone' : 1, 'email' : 1}")
    List<PastorDTO> findAllDTO();
}