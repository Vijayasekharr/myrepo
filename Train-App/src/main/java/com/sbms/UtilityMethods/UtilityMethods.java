package com.sbms.UtilityMethods;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbms.Entitys.Booking_infojava;
import com.sbms.Entitys.Quota;
import com.sbms.Entitys.TicketResponsejava;
import com.sbms.Entitys.TicketResponsejson;
import com.sbms.Entitys.Train_Search_Request;
import com.sbms.Entitys.Train_Search_Responsejson;
import com.sbms.Repositorys.TrainRepository;
import com.sbms.ServicesImpl.TicketServiceImpl;

@Component
public class UtilityMethods {

	@Autowired
	TrainRepository trainRepository;
	
	@Autowired
	TicketServiceImpl ticketServiceImpl;
	
	public HashMap<Integer, String> getAvailableTrainsBetweenTwoStations(String from_station, String to_station) {
		HashMap<Integer, String> availabletrains = new HashMap<>();
		List<String> findTrainsAtFromStationWithoutFiltering = trainRepository
				.findTrainsAtFromStationWithoutFiltering(from_station);

		List<String> findTrainsToStationWithFiltering = trainRepository
				.findTrainsToStationWithFiltering(to_station, findTrainsAtFromStationWithoutFiltering);

		List<String> findTrainsAtFromStationWithFiltering = trainRepository
				.findTrainsAtFromStationWithFiltering(from_station, findTrainsToStationWithFiltering);

		List<String> trainsBetweenSelectedFromStationAndToStation = findTrainsAtFromStationWithFiltering;
		
		System.out.println("The Available train names  between from "+ from_station + " to " + to_station
				+ " is :: " + trainsBetweenSelectedFromStationAndToStation);

		for (String string : trainsBetweenSelectedFromStationAndToStation) {
			System.out.println("Train Name :: "+string);
			Integer findTrainNumber = trainRepository.findTrainNumber(string);
			availabletrains.put(findTrainNumber, string);
		}
		System.out.println("The Available train no's  between from " + from_station + " to " + to_station
				+ " is :: " + availabletrains);

		return availabletrains;
	}
	
	public Train_Search_Responsejson setQuotaCost(Train_Search_Request train_Search_Request,Train_Search_Responsejson train_Search_Responsejson, Integer train_no) {
		
		Quota quota = new Quota();
		
		try {
			TicketResponsejava ticketResponsejava = ticketServiceImpl.bookTicket(new Booking_infojava(train_no, train_Search_Request.getFrom_station(),
					train_Search_Request.getTo_station(), "GEN", train_Search_Request.getDate()));
			quota.set_GEN_("Rs."+ticketResponsejava.getCost()+"");
		} catch (Exception e) {
			System.out.println("GEN Quota is not avilable for the train_name :: "+train_Search_Responsejson.getTrain_name()+" And train_no "+train_no);
		}
		
		try {
			TicketResponsejava ticketResponsejava = ticketServiceImpl.bookTicket(new Booking_infojava(train_no, train_Search_Request.getFrom_station(),
					train_Search_Request.getTo_station(), "SL", train_Search_Request.getDate()));
			quota.set_SL_("Rs."+ticketResponsejava.getCost()+"");
		} catch (Exception e) {
			System.out.println("SL Quota is not avilable for the train_name :: "+train_Search_Responsejson.getTrain_name()+" And train_no "+train_no);
		}
		
		try {
			TicketResponsejava ticketResponsejava = ticketServiceImpl.bookTicket(new Booking_infojava(train_no, train_Search_Request.getFrom_station(),
					train_Search_Request.getTo_station(), "3AC", train_Search_Request.getDate()));
			quota.set_3AC_("Rs."+ticketResponsejava.getCost()+"");
		} catch (Exception e) {
			System.out.println("3AC Quota is not avilable for the train_name :: "+train_Search_Responsejson.getTrain_name()+" And train_no "+train_no);
		}
		
		try {
			TicketResponsejava ticketResponsejava = ticketServiceImpl.bookTicket(new Booking_infojava(train_no, train_Search_Request.getFrom_station(),
					train_Search_Request.getTo_station(), "2AC", train_Search_Request.getDate()));
			quota.set_2AC_("Rs."+ticketResponsejava.getCost()+"");
		} catch (Exception e) {
			System.out.println("2AC Quota is not avilable for the train_name :: "+train_Search_Responsejson.getTrain_name()+" And train_no "+train_no);
		}
		
		try {
			TicketResponsejava ticketResponsejava = ticketServiceImpl.bookTicket(new Booking_infojava(train_no, train_Search_Request.getFrom_station(),
					train_Search_Request.getTo_station(), "1AC", train_Search_Request.getDate()));
			quota.set_1AC_("Rs."+ticketResponsejava.getCost()+"");
		} catch (Exception e) {
			System.out.println("1AC Quota is not avilable for the train_name :: "+train_Search_Responsejson.getTrain_name()+" And train_no "+train_no);
		}
		
		train_Search_Responsejson.setCost_for_each_Quota(quota);
		return train_Search_Responsejson;
		
	}
	
	
}



