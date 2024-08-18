package com.title.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.Supervisor;



@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    Supervisor findByCode(String code);
}