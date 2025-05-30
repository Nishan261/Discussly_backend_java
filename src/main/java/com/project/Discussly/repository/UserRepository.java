package com.project.Discussly.repository;

import com.project.Discussly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     Optional<User> getByEmail(String email);



}
