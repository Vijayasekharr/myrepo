package com.sbms.ServicesImpl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbms.Entitys.Booking_infojava;
import com.sbms.Entitys.Coach;
import com.sbms.Entitys.TicketResponsejava;
import com.sbms.Entitys.Train;
import com.sbms.Entitys.TrainCoachInfo;
import com.sbms.Entitys.Train_Search_Request;
import com.sbms.Entitys.Train_Search_Responsejava;
import com.sbms.Entitys.Train_Search_Responsejson;
import com.sbms.Repositorys.TrainRepository;
import com.sbms.ServicesI.TrainServiceI;
import com.sbms.UtilityMethods.UtilityMethods;

@Service
public class TrainServiceImpl implements TrainServiceI {

	@Autowired
	TrainRepository trainRepository;

	@Autowired
	UtilityMethods utilityMethods;

	@Override
	public Train save(Train trian) {
		Train save = trainRepository.save(trian);
		return save;
	}

	@Autowired
	public TicketServiceImpl ticketServiceImpl;

	@Override
	public List<Train> findAll() {
		List<Train> findAll = trainRepository.findAll();
		return findAll;
	}

	@Override
	public List<Train> saveAll(List<Train> trainList) {
		List<Train> saveAll = trainRepository.saveAll(trainList);
		return saveAll;
	}

	@Override
	public Optional<Train> findById(Integer id) {
		Optional<Train> findById = trainRepository.findById(id);
		return findById;
	}

	public List<Train_Search_Responsejson> trains_Search(Train_Search_Request train_Search_Request) {

		List<Train_Search_Responsejson> train_search_response_json_list = new ArrayList<>();

		HashMap<Integer, String> availableTrainsBetweenTwoStations = utilityMethods
				.getAvailableTrainsBetweenTwoStationsInTwoWay(train_Search_Request.getFrom_station().toUpperCase(),
						train_Search_Request.getTo_station().toUpperCase());

		Set<Integer> availableTrainNumbersBetweenTwoStations = availableTrainsBetweenTwoStations.keySet();
		System.out.println("availableTrainNumbersBetweenTwoStations ::" + availableTrainNumbersBetweenTwoStations);

		for (Integer train_no : availableTrainNumbersBetweenTwoStations) {

			Train_Search_Responsejava train_Search_Responsejava = new Train_Search_Responsejava();
			Train_Search_Responsejson train_Search_Responsejson = new Train_Search_Responsejson();
			Coach coach = new Coach();

			List<String> findAvailableCoachs = trainRepository.findAvailableCoachs(train_no);
			System.out.println("Available coachs for the train :: " + train_no + " " + findAvailableCoachs);
			findAvailableCoachs.forEach(e -> {
				TicketResponsejava ticketResponsejava = ticketServiceImpl
						.bookTicket(new Booking_infojava(train_no, train_Search_Request.getFrom_station(),
								train_Search_Request.getTo_station(), e, train_Search_Request.getDate()));

				train_Search_Responsejava.setArri_at_from_station(ticketResponsejava.getArri_at_from_station());
				train_Search_Responsejava.setArri_at_to_station(ticketResponsejava.getArri_at_to_station());
				train_Search_Responsejava.setDept_at_from_station(ticketResponsejava.getDept_at_from_station());
				train_Search_Responsejava.setDept_at_to_station(ticketResponsejava.getDept_at_to_station());
				train_Search_Responsejava.setFrom_date(ticketResponsejava.getFrom_date());
				train_Search_Responsejava.setTo_date(ticketResponsejava.getTo_date());
				train_Search_Responsejava.setTotal_distance(ticketResponsejava.getTotal_distance());
				train_Search_Responsejava.setTotal_journey_hours(ticketResponsejava.getTotal_journey_hours());
				train_Search_Responsejava.setTrain_name(ticketResponsejava.getTrain_name());
				train_Search_Responsejava.setTrain_no(ticketResponsejava.getTrain_no());

				if (e.equals("GEN"))
					coach.set_GEN_(ticketResponsejava.getCost() + "");
				else if (e.equals("2S"))
					coach.set_2S_(ticketResponsejava.getCost() + "");
				else if (e.equals("CC"))
					coach.set_CC_(ticketResponsejava.getCost() + "");
				else if (e.equals("SL"))
					coach.set_SL_(ticketResponsejava.getCost() + "");
				else if (e.equals("3AC"))
					coach.set_3AC_(ticketResponsejava.getCost() + "");
				else if (e.equals("2AC"))
					coach.set_2AC_(ticketResponsejava.getCost() + "");
				else if (e.equals("1AC"))
					coach.set_1AC_(ticketResponsejava.getCost() + "");

				System.out.println(
						"Coach = " + ticketResponsejava.getCoach() + " And Cost = " + ticketResponsejava.getCost());

				train_Search_Responsejson.setArri_at_from_station(train_Search_Responsejava.getArri_at_from_station()
						.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
				train_Search_Responsejson.setArri_at_to_station(train_Search_Responsejava.getArri_at_to_station()
						.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
				train_Search_Responsejson.setCost_for_each_Coach(train_Search_Responsejava.getCost_for_each_Coach());
				train_Search_Responsejson.setDept_at_from_station(train_Search_Responsejava.getDept_at_from_station()
						.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
				train_Search_Responsejson.setDept_at_to_station(train_Search_Responsejava.getDept_at_to_station()
						.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
				train_Search_Responsejson.setFrom_date(
						train_Search_Responsejava.getFrom_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				train_Search_Responsejson.setTo_date(
						train_Search_Responsejava.getTo_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				train_Search_Responsejson.setTotal_distance(train_Search_Responsejava.getTotal_distance());
				train_Search_Responsejson.setTotal_journey_hours(train_Search_Responsejava.getTotal_journey_hours());
				train_Search_Responsejson.setTrain_name(train_Search_Responsejava.getTrain_name());
				train_Search_Responsejson.setTrain_no(train_Search_Responsejava.getTrain_no());

			});
			train_Search_Responsejson.setCost_for_each_Coach(coach);

			/* Calling utility method */
//			Train_Search_Responsejson train_Search_Responsejson2 = utilityMethods.setCoachCost(train_Search_Request,train_Search_Responsejson , train_no);
			train_search_response_json_list.add(train_Search_Responsejson);
//			train_search_response_json_list.add(train_Search_Responsejson2);
		}

		List<Train_Search_Responsejson> collect = train_search_response_json_list.stream().filter(
				train -> !train.getTotal_journey_hours().startsWith("-") && !train.getTotal_distance().startsWith("-"))
				.collect(Collectors.toList());
		return collect;

	}

	@Override
	public List<Integer> getTrainNumbers() {
		List<Integer> trainNumbers = trainRepository.getTrainNumbers();
		return trainNumbers;
	}

	@Override
	public List<TrainCoachInfo> coachInfoofTrains(List<Integer> train_nos_list) {

		List<TrainCoachInfo> trainCoachInfoList = new ArrayList<>();

		for (Integer integer : train_nos_list) {
			
			TrainCoachInfo trainCoachInfo = new TrainCoachInfo();

			List<String> coachs = trainRepository.coachInfoofTrain(integer);
			trainCoachInfo.setTrain_no(integer);
			coachs.forEach(e -> {
				if (e.equals("GEN")) {
					trainCoachInfo.set_GEN_(1);
				}else if (e.equals("2S")) {
					trainCoachInfo.set_2S_(1);
				}else if (e.equals("CC")) {
					trainCoachInfo.set_CC_(1);
				}
				else if (e.equals("SL")) {
					trainCoachInfo.set_SL_(1);
				}else if (e.equals("3AC")) {
					trainCoachInfo.set_3AC_(1);
				}else if (e.equals("2AC")) {
					trainCoachInfo.set_2AC_(1);
				}else if (e.equals("1AC")) {
					trainCoachInfo.set_1AC_(1);
				}

			});
			
			trainCoachInfoList.add(trainCoachInfo);

		}

		return trainCoachInfoList;
	}
}
