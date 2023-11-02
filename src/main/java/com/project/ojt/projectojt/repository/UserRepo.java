package com.project.ojt.projectojt.repository;

import com.project.ojt.projectojt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String userEmail);
    User findByEmailAndPassword(String email, String password);

}
