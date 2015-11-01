package com.ajira.candy.service.impl;


import java.util.List;

import com.ajira.candy.repository.TicketRepository;
import com.ajira.candy.models.Ticket;
import com.ajira.candy.service.TicketService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/docs/ticket")
public class TicketServiceImpl implements TicketService {
    public enum status {
        CANCELLED, COMPLETED
    }

    public enum cancelledReason {
        ENDUSER, OTHERS
    }


    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public String addNewTicket(@RequestBody Ticket ticket) {
        Assert.notNull(ticket, "ticket is a required parameter");
        ticketRepository.save(ticket);
        return "Ticket succesfully created!";
    }

    @Override
    public void removeTicket(long id) {
        Assert.notNull(id, "id is a required parameter");
        ticketRepository.delete(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get all Tickets")
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    @RequestMapping(params = {"description"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get all Tickets by description")
    public List<Ticket> findAll(@RequestParam("description") String description) {
        Assert.hasLength(description, "Description is a required attribute");
        return ticketRepository.findByDescription(description);
    }

    @Override
    public List<Ticket> findByTenantId(String tenantId) {
        Assert.hasLength(tenantId, "tenantId is a required attribute");
        return ticketRepository.findByTenantId(tenantId);
    }

    @Override
    @RequestMapping(params = {"description", "status"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get all Tickets by description and status")
    public List<Ticket> findAll(@RequestParam("description") String description, @RequestParam("status") String status) {
        Assert.hasLength(description, "Description is a required attribute");
        Assert.hasLength(status, "Status is a required attribute");
        List<Ticket> result = null;
        if (status.equalsIgnoreCase("completed")) {
            result = ticketRepository.findByDescriptionStatusComplete(description, status);
        } else if (status.equalsIgnoreCase("cancelled")) {
            result = ticketRepository.findByDescriptionStatusCancel(description, status);
        }
        return result;
    }

    @Override
    @RequestMapping(params = {"description", "status", "reason"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get all Tickets by description, status and reason")
    public List<Ticket> findAll(@RequestParam("description") String description, @RequestParam("status") String status, @RequestParam("reason") String reason) {
        Assert.hasLength(description, "Description is a required attribute");
        Assert.hasLength(status, "Status is a required attribute");
        Assert.hasLength(reason, "Reason is a required attribute");
        List<Ticket> result = null;
        if (status.equalsIgnoreCase("others")) {
            result = ticketRepository.findByDescriptionStatusReason(description, status, reason);
        } else {
            result = ticketRepository.findByDescriptionStatus(description, status, reason);
        }
        return result;
    }

    @Override
    @RequestMapping(value = "/findByDescription", params = {"description"}, method = RequestMethod.GET)
    public List<Ticket> findByDescription(@RequestParam("description") String description) {
        Assert.hasLength(description, "Description is a required attribute");
        List<Ticket> result = ticketRepository.findByDescription(description);
        return result;
    }

    @Override
    @RequestMapping(value = "/findByStatusComplete", method = RequestMethod.GET)
    public List<Ticket> findByStatusComplete() {

        List<Ticket> result = ticketRepository.findAllByStatusComplete(status.COMPLETED.toString());
        return result;
    }

    @Override
    @RequestMapping(value = "/findByStatusCancel", method = RequestMethod.GET)
    public List<Ticket> findByStatusCancel() {
        List<Ticket> result = ticketRepository.findAllByStatusCancel(status.CANCELLED.toString());
        return result;

    }

    @Override
    @RequestMapping(value = "/findByStatusReason", method = RequestMethod.GET)
    public List<Ticket> findByStatusReason() {
        List<Ticket> result = ticketRepository.findAllByStatusReason(status.CANCELLED.toString(), cancelledReason.OTHERS.toString());
        return result;

    }


}
