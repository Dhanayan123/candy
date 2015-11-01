package com.ajira.candy.repository;

import java.util.List;
import javax.transaction.Transactional;

import com.ajira.candy.models.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query("select u.tenantId, u.createdBy, u.description, u.severity, u.status from Ticket u")
    List<Ticket> findAll();

    @Query("select u from Ticket u where u.tenantId = ?1")
    List<Ticket> findByTenantId(String tenantId);


    @Query("select u.tenantId, u.createdBy, u.description, u.severity, u.status from Ticket u where u.description = ?1")
    List<Ticket> findByDescription(String description);

    @Query("select u.createdBy, u.description, u.severity, u.status, u.comments from Ticket u where u.description = ?1 and u.status = ?2")
    List<Ticket> findByDescriptionStatusComplete(String description, String Status);

    @Query("select u.createdBy, u.description, u.severity, u.status, u.cancelledReason from Ticket u where u.description = ?1 and u.status = ?2")
    List<Ticket> findByDescriptionStatusCancel(String description, String Status);

    @Query("select u.createdBy, u.description, u.severity, u.status, u.cancelledReason, u.cancelledOtherDescription " +
            "from Ticket u where u.description = ?1 and u.status = ?2 and u.cancelledReason = ?3")
    List<Ticket> findByDescriptionStatusReason(String description, String status, String reason);

    @Query("select u.createdBy, u.description, u.severity, u.status, u.cancelledReason " +
            "from Ticket u where u.description = ?1 and u.status = ?2 and u.cancelledReason = ?3")
    List<Ticket> findByDescriptionStatus(String description, String status, String reason);

    @Query("select u.createdBy, u.description, u.severity, u.status from Ticket u where u.status = ?1")
    List<Ticket> findAllByStatusComplete(String status);

    @Query("select u.createdBy, u.description, u.severity, u.status, u.cancelledReason from Ticket u where u.status = ?1")
    List<Ticket> findAllByStatusCancel(String status);

    @Query("select u.createdBy, u.description, u.severity, u.status, u.cancelledReason, u.cancelledOtherDescription" +
            " from Ticket u where u.status = ?1 and u.cancelledReason = ?2")
    List<Ticket> findAllByStatusReason(String status, String reason);


}
