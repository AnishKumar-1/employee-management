package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.models.EmployeeModel;

public interface EmployeeRepo extends JpaRepository<EmployeeModel, Long>{
    boolean existsByUser_Id(Long userId); // note the underscore

}
