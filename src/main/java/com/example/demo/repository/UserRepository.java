package com.example.demo.repository;


import com.example.demo.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);

}
