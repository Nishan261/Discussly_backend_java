package com.project.Discussly.repository;

import com.project.Discussly.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken,Integer> {
    Optional<VerificationToken> findByToken(String token);
}
