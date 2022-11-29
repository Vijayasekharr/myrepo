package com.sbms.ServicesI;

import org.springframework.http.ResponseEntity;

import com.sbms.Entitys.Ticket;
import com.sbms.Entitys.TicketRequest;

public interface Ticket_ServicesI {
	
	public Ticket bookTicket(TicketRequest ticketRequest);
	
	public Object getTicket(Integer integer);

}
