package com.ajira.candy.service;


import com.ajira.candy.models.Ticket;

import java.util.List;

public interface TicketService {

    public String addNewTicket(Ticket ticket);

    public void removeTicket(long id);

    public List<Ticket> findAll();

    public List<Ticket> findAll(String description);

    public List<Ticket> findAll(String description, String status);

    public List<Ticket> findAll(String description, String status, String reason);

    public List<Ticket> findByDescription(String description);

    public List<Ticket> findByTenantId(String tenantId);

    public List<Ticket> findByStatusComplete();

    public List<Ticket> findByStatusCancel();

    public List<Ticket> findByStatusReason();

}
