package com.sbms.ServicesImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.sbms.Entitys.Ticket;
import com.sbms.Entitys.TicketRequest;
import com.sbms.Repository.TicketRepository;
import com.sbms.ServicesI.Ticket_ServicesI;

@Service
public class Ticket_ServicesImpl implements Ticket_ServicesI {

	@Autowired
	TicketRepository ticketRepository;

	@Override
	public Ticket bookTicket(TicketRequest ticketRequest) {

		/* Connecting to IRCTC-Application through WebClient */
		String URL = "http://localhost:2000/sbms/irctc/bookTicket";
		Ticket ticket = WebClient.create()
								 .post()
								 .uri(URL)
								 .headers(headers -> headers
								 .setBasicAuth("yatra", "yatra123"))
								 .bodyValue(ticketRequest)
								 .retrieve()
								 .bodyToMono(Ticket.class)
								 .block();

		ticket.setProvider("Yatra");
		Ticket save = ticketRepository.save(ticket);
		return save;
	}

	@Override
	public Object getTicket(Integer integer) {

		Object object = null;

		Optional<Ticket> findById = ticketRepository.findById(integer);
		if (findById.isPresent()) {
			return object = findById.get();
		}
		return object = "Invalid pnr Number !!! ";

	}

}
