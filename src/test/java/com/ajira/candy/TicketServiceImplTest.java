package com.ajira.candy;


import com.ajira.candy.models.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ajira.candy.service.impl.TicketServiceImpl;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
public class TicketServiceImplTest {

    public enum status {
        CANCELLED, COMPLETED
    }

    public enum cancelledReason {
        ENDUSER, OTHERS
    }

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Test
    public void B_addNewTicket() {
        log.info("Create new Ticket() \n");
        List<Ticket> ticketList = createSampleIssue();
        for (Ticket ticket : ticketList) {
            String result = ticketServiceImpl.addNewTicket(ticket);
        }
        List<Ticket> tickets = ticketServiceImpl.findByTenantId("test");
        assertEquals(" Count of Tickets - ", tickets.size(), 3);
    }


    @Test
    public void D_findAll() {
        log.info("Select Ticket By Description \n");
        List<Ticket> tickets = ticketServiceImpl.findAll("Test Network Outage");
        assertEquals(" Count of Tickets - ", tickets.size(), 1);

    }

    @Test
    public void E_findAll() {
        log.info("Select Ticket By Description and Status \n");

        List<Ticket> tickets = ticketServiceImpl.findAll("Test Network Outage", status.COMPLETED.toString());
        assertEquals(" Count of Tickets - ", tickets.size(), 1);

    }

    @Test
    public void F_findAll() {
        log.info("Select Ticket By Description and Status Cancelled\n");

        List<Ticket> tickets = ticketServiceImpl.findAll("Test Problem with service", status.CANCELLED.toString());
        assertEquals(" Count of Tickets - ", tickets.size(), 2);

    }

    @Test
    public void G_findAll() {
        log.info("Select Ticket By Description and Status and Reason\n");

        List<Ticket> tickets = ticketServiceImpl.findAll("Test Problem with service", status.CANCELLED.toString(), cancelledReason.OTHERS.toString());
        assertEquals(" Count of Tickets - ", tickets.size(), 1);

    }


    @Test
    public void Z_clean() {
        log.info("clean()");

        List<Ticket> tickets = ticketServiceImpl.findByTenantId("test");
        for (Ticket ticket : tickets) {
            ticketServiceImpl.removeTicket(ticket.getId());
        }
        tickets = ticketServiceImpl.findByTenantId("test");

        assertEquals(" Count of Tickets - ", tickets.size(), 0);

    }

    private List<Ticket> createSampleIssue() {
        List<Ticket> listTicket = new ArrayList();
        Ticket issueTicket = new Ticket();
        issueTicket.setTenantId("test");
        issueTicket.setCreatedBy("Dhanayan");
        issueTicket.setDescription("Test Network Outage");
        issueTicket.setSeverity(4);
        issueTicket.setStatus(status.COMPLETED.toString());
        issueTicket.setComments("Back to normal");
        listTicket.add(issueTicket);

        Ticket issueTicket1 = new Ticket();
        issueTicket1.setTenantId("test");
        issueTicket1.setCreatedBy("John snow");
        issueTicket1.setDescription("Test Problem with service");
        issueTicket1.setSeverity(5);
        issueTicket1.setStatus(status.CANCELLED.toString());
        issueTicket1.setCancelledReason(cancelledReason.ENDUSER.toString());
        listTicket.add(issueTicket1);

        Ticket issueTicket2 = new Ticket();
        issueTicket2.setTenantId("test");
        issueTicket2.setCreatedBy("Arya Stark");
        issueTicket2.setDescription("Test Problem with service");
        issueTicket2.setSeverity(5);
        issueTicket2.setStatus(status.CANCELLED.toString());
        issueTicket2.setCancelledReason(cancelledReason.OTHERS.toString());
        issueTicket.setCancelledOtherDescription("User");
        listTicket.add(issueTicket2);
        return listTicket;
    }


}