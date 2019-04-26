package com.bridgelabz.registerLoginService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.registerLoginService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
