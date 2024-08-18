package com.title.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByCode(String code);
}
