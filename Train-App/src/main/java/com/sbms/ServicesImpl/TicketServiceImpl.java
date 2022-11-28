package com.sbms.ServicesImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbms.Entitys.Booking_infojava;
import com.sbms.Entitys.TicketResponsejava;
import com.sbms.Entitys.Train;
import com.sbms.Repositorys.TrainRepository;
import com.sbms.ServicesI.TicketServiceI;

@Service
public class TicketServiceImpl implements TicketServiceI {

	@Autowired
	private TrainRepository trainRepository;

	@Autowired
	private TicketResponsejava ticketResponsejava;

	@Override
	public TicketResponsejava bookTicket(Booking_infojava booking_infojava) {
		System.out.println("Booking_infojava object is :: " + booking_infojava);
		Train findTrainAt_from_station = trainRepository.findTrainAt_from_station(
				booking_infojava.getFrom_station().toUpperCase(), booking_infojava.getQuota().toUpperCase(),
				booking_infojava.getTrain_no());
		Train findTrainAt_to_station = trainRepository.findTrainAt_to_station(
				booking_infojava.getTo_station().toUpperCase(), booking_infojava.getQuota().toUpperCase(),
				booking_infojava.getTrain_no());

		System.out.println(findTrainAt_from_station);
		System.out.println(findTrainAt_to_station);
		Double cost_at_fromstation = findTrainAt_from_station.getAmount();
		LocalTime arrivaltime_at_fromstation = findTrainAt_from_station.getArrival();
		LocalTime departuretime_at_fromstaton = findTrainAt_from_station.getDeparture();

		Double cost_at_tostation = findTrainAt_to_station.getAmount();
		LocalTime arrivaltime_at_tostation = findTrainAt_to_station.getArrival();
		LocalTime departuretime_at_tostaton = findTrainAt_to_station.getDeparture();

		Double ticket_cost = cost_at_tostation - cost_at_fromstation;
		Integer total_distance = findTrainAt_to_station.getKm() - findTrainAt_from_station.getKm();

		ticketResponsejava.setTrain_name(findTrainAt_from_station.getTrain_name());
		ticketResponsejava.setArri_at_from_station(arrivaltime_at_fromstation);
		ticketResponsejava.setArri_at_to_station(arrivaltime_at_tostation);
		ticketResponsejava.setCost(ticket_cost);
		ticketResponsejava.setDept_at_from_station(departuretime_at_fromstaton);
		ticketResponsejava.setDept_at_to_station(departuretime_at_tostaton);
		ticketResponsejava.setFrom_station(findTrainAt_from_station.getStation());
		ticketResponsejava.setQuota(findTrainAt_from_station.getQuota());
		ticketResponsejava.setTo_station(findTrainAt_to_station.getStation());
		ticketResponsejava.setTrain_no(booking_infojava.getTrain_no());
		ticketResponsejava.setTotal_distance(total_distance + " KM");

//		LocalDateTime Object at from station
		Integer day_at_from_station = findTrainAt_from_station.getDay();
		LocalDateTime datetime_at_fromstation = LocalDateTime.of(booking_infojava.getDate().getYear(),
				booking_infojava.getDate().getMonth(), booking_infojava.getDate().getDayOfMonth(),
				findTrainAt_from_station.getDeparture().getHour(), findTrainAt_from_station.getDeparture().getMinute());
		System.out.println("DateTime at from station " + datetime_at_fromstation);

//		LocalDateTime Object at from station		
		Integer day_at_to_station = findTrainAt_to_station.getDay();
		LocalDateTime datetime_at_tostation = LocalDateTime.of(booking_infojava.getDate().getYear(),
				booking_infojava.getDate().getMonth(),
				booking_infojava.getDate().getDayOfMonth() + day_at_to_station - day_at_from_station,
				findTrainAt_to_station.getArrival().getHour(), findTrainAt_to_station.getArrival().getMinute());
		System.out.println("DateTime at to station " + datetime_at_tostation);

		ticketResponsejava.setFrom_date(booking_infojava.getDate());
		ticketResponsejava.setTo_date(booking_infojava.getDate().plusDays(day_at_to_station - day_at_from_station));
		ticketResponsejava.setTotal_journey_hours(
				TicketServiceImpl.getTotalJourneyHours(datetime_at_fromstation, datetime_at_tostation));

		return ticketResponsejava;
	}

	private static String getTotalJourneyHours(LocalDateTime datetime_at_fromstation,
			LocalDateTime datetime_at_tostation) {
		Duration between = Duration.between(datetime_at_fromstation, datetime_at_tostation);
		long minutes = between.toMinutes();
		int hours = (int) (minutes / 60);
		int mins = (int) (minutes - (hours * 60));
		String totalJourneyTime = hours + ":" + mins + ":" + "00";
		return totalJourneyTime;
	}

	@Override
	public List<String> getAvailable_Stations_between_Two_Stations(String from_station, String to_station, String quota,
			Integer train_no) {
		System.out.println("from_station : "+from_station+" :: "+"to_station : "+to_station+" :: "+"quota : "+quota+" :: "+" train_no :"+train_no);
		Integer trainId_At_From_Station = trainRepository.getTrainId_At_From_Station(from_station, quota, train_no);
		Integer trainId_At_to_Station = trainRepository.getTrainId_At_to_Station(to_station, quota, train_no);
		
		List<String> available_Stations_between_Two_Stations = trainRepository
				.getAvailable_Stations_between_Two_Stations(trainId_At_From_Station, trainId_At_to_Station);

		return available_Stations_between_Two_Stations;
	}

}
