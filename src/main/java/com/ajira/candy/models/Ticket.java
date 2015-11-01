package com.ajira.candy.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Data
@Table(name = "ticket")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String tenantId;

    private String createdBy;

    private String description;

    private int severity;

    private String cancelledOtherDescription;

    private String comments;

    private String status;

    private String cancelledReason;

}