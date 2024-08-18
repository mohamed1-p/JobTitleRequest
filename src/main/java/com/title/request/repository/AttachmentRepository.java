package com.title.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.Attachment;
import com.title.request.models.Request;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	List<Attachment> findByRequest(Request request);
}
