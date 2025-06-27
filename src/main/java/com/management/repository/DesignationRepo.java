package com.management.repository;

import com.management.models.DesignationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignationRepo extends JpaRepository<DesignationModel,Long> {
    public Optional<DesignationModel> findByTitle(String title);
}
