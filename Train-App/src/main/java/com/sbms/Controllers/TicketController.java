package com.sbms.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbms.Entitys.Booking_infojava;
import com.sbms.Entitys.Booking_infojson;
import com.sbms.Entitys.StationsList;
import com.sbms.Entitys.Stations_bw_TwoStations;
import com.sbms.Entitys.TicketResponsejava;
import com.sbms.Entitys.TicketResponsejson;
import com.sbms.ServicesImpl.TicketServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TicketController {

	@Autowired
	TicketServiceImpl ticketServiceImpl;

	@Autowired
	Environment environment;

	@Autowired
	StationsList stationsList;

	@Autowired
	Booking_infojava booking_infojava;

	@Autowired
	TicketResponsejson ticketResponsejson;

	@PostMapping(value = "/bookTicket", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<TicketResponsejson> bookTicket(@RequestBody Booking_infojson booking_infojson) {

		LocalDate date = LocalDate.parse(booking_infojson.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		booking_infojava.setDate(date);
		booking_infojava.setFrom_station(booking_infojson.getFrom_station());
		booking_infojava.setQuota(booking_infojson.getQuota());
		booking_infojava.setTo_station(booking_infojson.getTo_station());
		booking_infojava.setTrain_no(booking_infojson.getTrain_no());

		TicketResponsejava ticketResponsejava = ticketServiceImpl.bookTicket(booking_infojava);

		ticketResponsejson.setTrain_no(ticketResponsejava.getTrain_no());
		ticketResponsejson.setTrain_name(ticketResponsejava.getTrain_name());
		ticketResponsejson.setFrom_station(ticketResponsejava.getFrom_station());
		ticketResponsejson
				.setFrom_date(ticketResponsejava.getFrom_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		ticketResponsejson.setArri_at_from_station(
				ticketResponsejava.getArri_at_from_station().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		ticketResponsejson.setDept_at_from_station(
				ticketResponsejava.getDept_at_from_station().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		ticketResponsejson.setBoarding_station(ticketResponsejava.getFrom_station());
		ticketResponsejson
				.setBoarding_date(ticketResponsejava.getFrom_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		ticketResponsejson.setArri_at_boarding_station(
				ticketResponsejava.getArri_at_from_station().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		ticketResponsejson.setDept_at_boarding_station(
				ticketResponsejava.getDept_at_from_station().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		ticketResponsejson.setTo_station(ticketResponsejava.getTo_station());
		ticketResponsejson.setArri_at_to_station(
				ticketResponsejava.getArri_at_to_station().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		ticketResponsejson.setDept_at_to_station(
				ticketResponsejava.getDept_at_to_station().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		ticketResponsejson
				.setTo_date(ticketResponsejava.getTo_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		ticketResponsejson.setTotal_journey_hours(ticketResponsejava.getTotal_journey_hours());
		ticketResponsejson.setCost(ticketResponsejava.getCost());
		ticketResponsejson.setQuota(ticketResponsejava.getQuota());
		ticketResponsejson.setTotal_distance(ticketResponsejava.getTotal_distance());

		log.info("[ The bookTicket Request Processing by the TRAIN-APPLICATION which run's on the Port :: "
				+ environment.getProperty("server.port") + " ]");
		return new ResponseEntity<TicketResponsejson>(ticketResponsejson, HttpStatus.OK);
	}

	@GetMapping("/getAvailable_Stations_between_Two_Stations")
	public ResponseEntity<StationsList> getAvailable_Stations_between_Two_Stations(@RequestParam String from_station,
			@RequestParam String to_station, @RequestParam String quota, @RequestParam Integer train_no) {

		List<String> available_Stations_between_Two_Stations = ticketServiceImpl
				.getAvailable_Stations_between_Two_Stations(from_station.toUpperCase(), to_station.toUpperCase(), quota.toUpperCase(), train_no);
		return new ResponseEntity<StationsList>(new StationsList(available_Stations_between_Two_Stations),
				HttpStatus.OK);

	}

	@PostMapping("/getAvailable_Stations_between_Two_Stations")
	public ResponseEntity<StationsList> get_Available_Stations_between_Two_Stations(@RequestBody
			Stations_bw_TwoStations stations_bw_TwoStations) {
		System.out.println(stations_bw_TwoStations);

		ResponseEntity<StationsList> available_Stations_between_Two_Stations = this
				.getAvailable_Stations_between_Two_Stations(stations_bw_TwoStations.getFrom_station().toUpperCase(),
						stations_bw_TwoStations.getTo_station().toUpperCase(), stations_bw_TwoStations.getQuota().toUpperCase(),
						stations_bw_TwoStations.getTrain_no());
		return available_Stations_between_Two_Stations;

	}

}
