package com.title.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.title.request.models.RequestStatus;

@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer>{

}
