package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.dto.SecretaryDTO;
import com.fabianospdev.volunteer.models.Secretary;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretaryRepositoryExtension {
    @Query("{}")
    List<Secretary> findAllList();

    @Query(value="{}", fields="{ 'id' : 1, 'name' : 1, 'phone' : 1, 'email' : 1}")
    List<SecretaryDTO> findAllDTO();
}