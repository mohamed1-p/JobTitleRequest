package com.title.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.AttachmentType;

@Repository
public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, Long> {
    AttachmentType findByCode(String code);
}