package com.sbms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbms.Entitys.Availabe_Trains;
import com.sbms.Entitys.Train_Search_Requestjson;
import com.sbms.ServicessI.TrainServicesI;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TrainController {

	@Autowired
	TrainServicesI trainServicesI;

	@Autowired
	Environment environment;

	@GetMapping("/train_search")
	public ResponseEntity<Availabe_Trains> train_search(@RequestParam("from_station") String from_station,
			@RequestParam("to_station") String to_station, @RequestParam("date") String date)
	{

		Train_Search_Requestjson train_Search_Requestjson = new Train_Search_Requestjson();
		train_Search_Requestjson.setFrom_station(from_station);
		train_Search_Requestjson.setTo_station(to_station);
		train_Search_Requestjson.setDate(date);

		log.info("The trains between " + train_Search_Requestjson.getFrom_station() + " and "
				+ train_Search_Requestjson.getTo_station() + " is  processing by "
				+ "TrainController of IRCTC-Application");

		System.out.println("Train_Search_Requestjson is :: " + train_Search_Requestjson);

		Availabe_Trains train_search = trainServicesI.train_search(train_Search_Requestjson);
		log.info("[ The bookTicket Request Processing by the IRCTC-APPLICATION which run's on the Port :: "
				+ environment.getProperty("server.port") + " ]");
		return new ResponseEntity<Availabe_Trains>(train_search, HttpStatus.OK);

	}

}
