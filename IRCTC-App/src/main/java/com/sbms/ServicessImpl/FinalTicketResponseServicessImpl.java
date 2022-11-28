package com.sbms.ServicessImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sbms.Entitys.Booking_info;
import com.sbms.Entitys.FinalTicketResponse;
import com.sbms.Entitys.InitialTicketResponse;
import com.sbms.Entitys.StationsList;
import com.sbms.Entitys.Stations_bw_TwoStations;
import com.sbms.Entitys.TicketRequest;
import com.sbms.Entitys.Updated_Booking_infojson;
import com.sbms.FeignClients.FeignClientToTrainApplication;
import com.sbms.Repositorys.FinalTicketResponseRepository;
import com.sbms.ServicessI.FinalTicketResponseServicessI;
import com.sbms.UtilityMethods.UtilityMethods;

@Service
public class FinalTicketResponseServicessImpl implements FinalTicketResponseServicessI {

	@Autowired
	Booking_info booking_info;

	@Autowired
	Updated_Booking_infojson updated_Booking_infojson;

	@Autowired
	Stations_bw_TwoStations stations_bw_TwoStations;

//	@Autowired
//	FinalTicketResponse finalTicketResponse;

	@Autowired
	UtilityMethods utilityMethods;

	@Autowired
	FinalTicketResponseRepository finalTicketResponseRepository;

	@Autowired
	FeignClientToTrainApplication feignClientToTrainApplication;

	@Override
	public FinalTicketResponse bookTicket(TicketRequest ticketRequest) {

		FinalTicketResponse finalTicketResponse = new FinalTicketResponse();

		booking_info.setFrom_station(ticketRequest.getFrom_station());
		booking_info.setQuota(ticketRequest.getQuota());
		booking_info.setTo_station(ticketRequest.getTo_station());
		booking_info.setTrain_no(ticketRequest.getTrain_no());
		booking_info.setDate(ticketRequest.getDate());

		/* Connecting to Train Application through Feign-Client */
		InitialTicketResponse initialTicketResponse = feignClientToTrainApplication.bookTicket(booking_info);

		/* Connecting to Train Application through WebClient */
//		InitialTicketResponse initialTicketResponse = WebClient.create()
//				 .post()
//				 .uri("http://swapna:1011/bookTicket")
//				 .bodyValue(booking_info)
//				 .retrieve()
//				 .bodyToMono(InitialTicketResponse.class)
//				 .block();

		finalTicketResponse.setTrain_no(initialTicketResponse.getTrain_no());
		finalTicketResponse.setTrain_name(initialTicketResponse.getTrain_name());
		finalTicketResponse.setFrom_station(initialTicketResponse.getFrom_station());
		finalTicketResponse.setFrom_date(initialTicketResponse.getFrom_date());
		finalTicketResponse.setArri_at_from_station(initialTicketResponse.getArri_at_from_station());
		finalTicketResponse.setDept_at_from_station(initialTicketResponse.getDept_at_from_station());
		finalTicketResponse.setBoarding_station(initialTicketResponse.getBoarding_station());
		finalTicketResponse.setBoarding_date(initialTicketResponse.getBoarding_date());
		finalTicketResponse.setArri_at_boarding_station(initialTicketResponse.getArri_at_boarding_station());
		finalTicketResponse.setDept_at_boarding_station(initialTicketResponse.getDept_at_boarding_station());
		finalTicketResponse.setTo_station(initialTicketResponse.getTo_station());
		finalTicketResponse.setArri_at_to_station(initialTicketResponse.getArri_at_to_station());
		finalTicketResponse.setDept_at_to_station(initialTicketResponse.getDept_at_to_station());
		finalTicketResponse.setTo_date(initialTicketResponse.getTo_date());
		finalTicketResponse.setTotal_J_H_from_from_station(initialTicketResponse.getTotal_journey_hours());
		finalTicketResponse.setTotal_J_H_from_boarding_station(initialTicketResponse.getTotal_journey_hours());
		finalTicketResponse.setTotal_dist_from_from_station(initialTicketResponse.getTotal_distance());
		finalTicketResponse.setTotal_dist_from_boarding_station(initialTicketResponse.getTotal_distance());
		finalTicketResponse.setCost(initialTicketResponse.getCost());
		finalTicketResponse.setQuota(initialTicketResponse.getQuota());

		finalTicketResponse.setPassenger(ticketRequest.getPassenger());

		FinalTicketResponse save = finalTicketResponseRepository.save(finalTicketResponse);
		return save;
	}

	@Override
	public String deleteTicket(Integer pnr) {
		try {
			finalTicketResponseRepository.deleteById(pnr);
		} catch (Exception e) {
			return "Invalid pnr Number !!!";
		}
		return "The Ticket with PNR :: " + pnr + " deleted successfully";
	}

	public Optional<FinalTicketResponse> getTicket(Integer pnr) {
		Optional<FinalTicketResponse> findById = finalTicketResponseRepository.findById(pnr);
		return findById;
	}

	@Override
	public ResponseEntity updateTicket(FinalTicketResponse finalTicketResponse1,
			FinalTicketResponse finalTicketResponse2) throws CloneNotSupportedException {

		FinalTicketResponse finalTicketResponse3 = (FinalTicketResponse) finalTicketResponse1.clone();
		FinalTicketResponse finalTicketResponse4 = (FinalTicketResponse) finalTicketResponse2.clone();

		finalTicketResponse3.setBoarding_station("");
		finalTicketResponse4.setBoarding_station("");

		System.out.println("finalTicketResponse1 is :: " + finalTicketResponse1);
		System.out.println("finalTicketResponse2 is :: " + finalTicketResponse2);
		System.out.println("finalTicketResponse3 is :: " + finalTicketResponse3);
		System.out.println("finalTicketResponse4 is :: " + finalTicketResponse4);

		if (finalTicketResponse1.getBoarding_station().equals(finalTicketResponse2.getBoarding_station())) {

			return new ResponseEntity<String>(
					"Hey Man !!!, Whatever the Boarding Station you Supplied that Boarding point is same as Existed one",
					HttpStatus.BAD_REQUEST);
		} else if (finalTicketResponse3.equals(finalTicketResponse4) == false) {

			if (!finalTicketResponse1.getLast_updated_on().equals(finalTicketResponse2.getLast_updated_on())
					&& !finalTicketResponse1.getBoarding_date().equals(finalTicketResponse2.getBoarding_date())
					&& !finalTicketResponse1.getArri_at_boarding_station()
							.equals(finalTicketResponse2.getArri_at_boarding_station())
					&& !finalTicketResponse1.getDept_at_boarding_station()
							.equals(finalTicketResponse2.getDept_at_boarding_station())
					&& !finalTicketResponse1.getTotal_dist_from_boarding_station()
							.equals(finalTicketResponse2.getTotal_dist_from_boarding_station())

			) {
				return new ResponseEntity<String>(
						"    Hey Man !!!, You already updated the Boarding Station !!!, For Updating one more time, Please pass that Updated Ticket \n      By changing the Boarding Station in the Request Body."
								+ " To get that updated Ticket Please CLICK on the following URL                                                    ***   /getTicket/"
								+ finalTicketResponse1.getPnr() + "   ***",
						HttpStatus.BAD_REQUEST);
			} else {

				return new ResponseEntity<String>(
						"Hey Man !!!, We can updated only Boarding Station other than that you can't Update Anything",
						HttpStatus.BAD_REQUEST);
			}

		} else {
			
			stations_bw_TwoStations.setFrom_station(finalTicketResponse2.getBoarding_station());
			stations_bw_TwoStations.setTo_station(finalTicketResponse1.getTo_station());
			stations_bw_TwoStations.setQuota(finalTicketResponse1.getQuota());
			stations_bw_TwoStations.setTrain_no(finalTicketResponse1.getTrain_no());

//			StationsList available_Stations_between_Two_Stations = feignClientToTrainApplication
//					.getAvailable_Stations_between_Two_Stations(finalTicketResponse1.getFrom_station(),
//							finalTicketResponse1.getTo_station(), finalTicketResponse1.getQuota(),
//							finalTicketResponse1.getTrain_no());

			StationsList available_Stations_between_Two_Stations = feignClientToTrainApplication
					.getAvailable_Stations_between_Two_Stations(stations_bw_TwoStations);

			List<String> stationsList = available_Stations_between_Two_Stations.getStationsList();
			stationsList.remove(finalTicketResponse2.getBoarding_station());
			stationsList.remove(finalTicketResponse1.getTo_station());

			System.out.println("The Stations between " + finalTicketResponse2.getBoarding_station() + " And "
					+ finalTicketResponse1.getTo_station() + " is :: " + stationsList);

			if (stationsList.contains(finalTicketResponse1.getBoarding_station())) {

				booking_info.setFrom_station(finalTicketResponse1.getFrom_station());
				booking_info.setQuota(finalTicketResponse1.getQuota());
				booking_info.setTo_station(finalTicketResponse1.getBoarding_station());
				booking_info.setTrain_no(finalTicketResponse1.getTrain_no());
				booking_info.setDate(finalTicketResponse1.getFrom_date());

				InitialTicketResponse initialTicketResponse = feignClientToTrainApplication.bookTicket(booking_info);
				finalTicketResponse1.setBoarding_date(initialTicketResponse.getTo_date());
				finalTicketResponse1.setArri_at_boarding_station(initialTicketResponse.getArri_at_to_station());
				finalTicketResponse1.setDept_at_boarding_station(initialTicketResponse.getDept_at_to_station());
				finalTicketResponse1.setTotal_dist_from_boarding_station(
						(Integer.parseInt(finalTicketResponse1.getTotal_dist_from_from_station().substring(0,
								finalTicketResponse1.getTotal_dist_from_from_station().length() - 3)))
								- (Integer.parseInt(initialTicketResponse.getTotal_distance().substring(0,
										initialTicketResponse.getTotal_distance().length() - 3)))
								+ " KM");

//				caluclation  of total Journey hours from boarding station to To station 
				String get_Total_J_H_Bw_BoardingAndToStation = utilityMethods
						.get_Total_J_H_Bw_BoardingAndToStation(finalTicketResponse1);
				finalTicketResponse1.setTotal_J_H_from_boarding_station(get_Total_J_H_Bw_BoardingAndToStation);

//				Setting Last updated date and time to the FinalTicketResponse1
				LocalDateTime localDateTime = LocalDateTime.now();
				DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				finalTicketResponse1.setLast_updated_on(localDateTime.format(ofPattern));

//				Saving in to the DB
				FinalTicketResponse save = finalTicketResponseRepository.save(finalTicketResponse1);

				return new ResponseEntity<FinalTicketResponse>(save, HttpStatus.OK);

			} else {

				String string = stationsList.toString();
				return new ResponseEntity<String>(
						"Hey Man !!!, You Choosen the Wrong *** Boarding Station *** !!!, \n The available Stations between "
								+ finalTicketResponse2.getBoarding_station() + " And "
								+ finalTicketResponse1.getTo_station() + " is :: \n " + string,
						HttpStatus.BAD_REQUEST);
			}

		}

	}

}