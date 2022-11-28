package com.sbms.ServicesI;

import java.util.List;

import com.sbms.Entitys.Booking_infojava;
import com.sbms.Entitys.TicketResponsejava;

public interface TicketServiceI {
	
	public TicketResponsejava bookTicket(Booking_infojava booking_info);
	
	public List<String> getAvailable_Stations_between_Two_Stations(String from_station,String to_station,String quota,Integer train_no);
	
}
