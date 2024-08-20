package com.title.request.repository;

import java.time.LocalDateTime;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.Request;
import com.title.request.models.RequestStatus;
import com.title.request.models.UserEntity;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

	Page<Request> findByCreator(UserEntity creator,Pageable pageable);
    Page<Request> findByRequestStatus(RequestStatus requestStatus,Pageable pageable);
    Page<Request> findByRequestDateBetween(LocalDateTime startDate, LocalDateTime endDate,Pageable pageable);
}
