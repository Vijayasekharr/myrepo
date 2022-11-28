package com.sbms.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.activation.DataSource;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.bouncycastle.crypto.engines.ISAACEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sbms.Entitys.Availabe_Trains;
import com.sbms.Entitys.Train;
import com.sbms.Entitys.Train_Search_Request;
import com.sbms.Entitys.Train_Search_Requestjson;
import com.sbms.Entitys.Train_Search_Responsejson;
import com.sbms.Entitys.Trains;
import com.sbms.ServicesI.TrainServiceI;
import com.sbms.ServicesImpl.TrainServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TrainController {
	
	@Autowired
	TrainServiceImpl trainServiceImpl;
	
	@Autowired
	Environment environment;	
	
	@PostMapping(value = "/saveTrain", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> saveTrain(@RequestBody Train train){
		Train save = trainServiceImpl.save(train);
		return new ResponseEntity<String>("TRAIN INFORMATION SAVED SUCCESFULLY WITH AN ID :: "+save.getId(), HttpStatus.CREATED);
		
	}
	
	@GetMapping(value="/findAllTrains", produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Trains> findAllTrains(){
		log.info("Request came to findAllTrains");
		List<Train> findAll = trainServiceImpl.findAll();
		Trains trains = new Trains(findAll);
		return new ResponseEntity<Trains>(trains, HttpStatus.FOUND);
	}
	
	@GetMapping(value="/findByIdTrain/{id}")
	public ResponseEntity findByIdTrain(@PathVariable Integer id) {
		Optional<Train> findById = trainServiceImpl.findById(id);
		if(findById.isPresent()) {
			return new ResponseEntity<Train>(findById.get(),HttpStatus.FOUND);
		}
		else 
			return new ResponseEntity<String>("The TRAIN WAS NOT FOUND WITH THE ID :: "+id,HttpStatus.BAD_REQUEST);
		
	}
	@PostMapping(value="/saveAllTrains", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> saveAllTrains(@RequestBody Trains trains){
		String string = "";
		List<Train> saveAll = trainServiceImpl.saveAll(trains.getTrainlistList());
		for (Train train : saveAll) {
			string=string+", "+train.getId();
		}
		return new ResponseEntity<String>("ALL THE TICKETS ARE SAVED WITH SUCCESSFULLY WITH AN ID'S :: "+string, HttpStatus.CREATED);
	}
	
	
	
	
	@PostMapping("/train_search")
	public ResponseEntity<Availabe_Trains> trains_Search(@RequestBody Train_Search_Requestjson train_Search_Requestjson) {
		log.info("The trains between "+train_Search_Requestjson.getFrom_station()+" and "+train_Search_Requestjson.getTo_station()+" is  processing by "+ "TrainController of Train-Application");
		System.out.println("Train_Search_Responsejson ISAACEngine :: " +train_Search_Requestjson);
		LocalDate date = LocalDate.parse(train_Search_Requestjson.getDate(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		Train_Search_Request train_Search_Requestjava = new Train_Search_Request();
		train_Search_Requestjava.setDate(date);
		train_Search_Requestjava.setFrom_station(train_Search_Requestjson.getFrom_station());
		train_Search_Requestjava.setTo_station(train_Search_Requestjson.getTo_station());
		List<Train_Search_Responsejson> trains_Search = trainServiceImpl.trains_Search(train_Search_Requestjava);
		log.info("[ The train_search Request Processing by the TRAIN-APPLICATION which run's on the Port :: "+environment.getProperty("server.port") +" ]");
		return new ResponseEntity<Availabe_Trains>(new Availabe_Trains(trains_Search) ,HttpStatus.OK);
		
	}

}
