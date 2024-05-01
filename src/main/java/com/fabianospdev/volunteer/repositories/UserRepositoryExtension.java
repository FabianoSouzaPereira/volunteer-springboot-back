package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.models.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryExtension {
    @Query("{}")
    List<User> findAllList();
}