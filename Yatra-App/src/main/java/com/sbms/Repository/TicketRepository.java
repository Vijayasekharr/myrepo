package com.sbms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbms.Entitys.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
