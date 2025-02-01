package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.models.UserModel;

@Repository
public interface UserModelRepo extends JpaRepository<UserModel, Long>{

    public UserModel findByEmail(String email);

}
