package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.models.DepartmentModel;

public interface DepartmentRepo extends JpaRepository<DepartmentModel, Long>{
  
}
