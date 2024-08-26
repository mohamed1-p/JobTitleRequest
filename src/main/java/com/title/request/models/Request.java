package com.title.request.models;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

    @ManyToOne
    @JoinColumn(name = "head_department_id")
    private HeadDepartment headDepartment;

    @ManyToOne
    @JoinColumn(name = "unit_head_id")
    private UnitHead unitHead;

    @ManyToOne(optional = false)
    @JoinColumn(name = "request_status_id", nullable = false)
    private RequestStatus requestStatus;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    private String comments;

    
    @ManyToOne
    @JoinColumn(name = "user_admin_id")
    private UserEntity userAdmin;

    
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

   
    
    
    
    
    
    
    
    
    
    
    
    
    
}