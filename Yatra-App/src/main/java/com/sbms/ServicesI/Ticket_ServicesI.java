package com.sbms.ServicesI;

import com.sbms.Entitys.FinalTicketResponse;
import com.sbms.Entitys.TicketRequest;

public interface Ticket_ServicesI {
	
	public FinalTicketResponse bookTicket(TicketRequest ticketRequest);

}
