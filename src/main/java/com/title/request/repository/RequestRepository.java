package com.title.request.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.Request;
import com.title.request.models.RequestStatus;
import com.title.request.models.UserEntity;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

	List<Request> findByCreator(UserEntity creator);
    List<Request> findByRequestStatus(RequestStatus requestStatus);
    List<Request> findByRequestDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
