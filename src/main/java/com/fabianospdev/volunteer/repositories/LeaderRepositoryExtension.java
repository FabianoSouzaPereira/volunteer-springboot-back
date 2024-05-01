package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.dto.LeaderDTO;
import com.fabianospdev.volunteer.models.Leader;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderRepositoryExtension {
    @Query("{}")
    List<Leader> findAllList();

    @Query(value="{}", fields="{ 'id' : 1, 'name' : 1, 'phone' : 1, 'email' : 1}")
    List<LeaderDTO> findAllDTO();
}