package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.models.ProjectModel;

public interface ProjectRepo extends JpaRepository<ProjectModel, Long>{

}
