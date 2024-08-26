package com.title.request.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.UnitHead;

@Repository
public interface UnitHeadRepository extends JpaRepository<UnitHead, Long> {
	Optional<UnitHead> findByCode(String code);
}