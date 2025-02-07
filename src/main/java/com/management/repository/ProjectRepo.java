package com.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.models.ProjectModel;

public interface ProjectRepo extends JpaRepository<ProjectModel, Long>{
  public  Optional<ProjectModel> findByName(String projectName);
}
