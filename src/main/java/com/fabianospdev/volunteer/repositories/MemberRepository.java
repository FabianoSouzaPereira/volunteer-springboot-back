package com.fabianospdev.volunteer.repositories;

import com.fabianospdev.volunteer.model.Member;
import com.fabianospdev.volunteer.model.MemberRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

    List<Member> findByRolesContaining(MemberRole role);

    Optional<Member> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, String id);
}
