package com.sbms.ServicessI;

import com.sbms.Entitys.TicketRequest;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.sbms.Entitys.FinalTicketResponse;

public interface FinalTicketResponseServicessI {
	
	public FinalTicketResponse bookTicket(TicketRequest ticket);
	
	public String deleteTicket(Integer pnr);
	
	public Optional<FinalTicketResponse> getTicket(Integer pnr);
	
	public ResponseEntity updateTicket(FinalTicketResponse  finalTicketResponse1, FinalTicketResponse finalTicketResponse2) throws CloneNotSupportedException;

}
