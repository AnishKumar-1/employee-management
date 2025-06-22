package com.management.repository;

import com.management.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserModelRepo extends JpaRepository<Users, Long>{

    public Users findByEmail(String email);

}
