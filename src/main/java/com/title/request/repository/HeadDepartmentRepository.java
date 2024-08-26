package com.title.request.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.HeadDepartment;

@Repository
public interface HeadDepartmentRepository extends JpaRepository<HeadDepartment, Long> {
    Optional<HeadDepartment> findByCode(String code);
}