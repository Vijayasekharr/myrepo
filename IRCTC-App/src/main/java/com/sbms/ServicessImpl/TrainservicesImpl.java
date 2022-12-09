package com.sbms.ServicessImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sbms.Entitys.Availabe_Trains;
import com.sbms.Entitys.Train_Search_Requestjson;
import com.sbms.Entitys.Train_Search_Responsejson;
import com.sbms.FeignClients.FeignClientToTrainApplication;
import com.sbms.ServicessI.TrainServicesI;

@Service
public class TrainservicesImpl implements TrainServicesI {

	@Autowired
	FeignClientToTrainApplication feignClientToTrainApplication;

	@Override
	public Availabe_Trains train_search(Train_Search_Requestjson train_Search_Requestjson) {

		/* Connecting to Train Application through Feign-Client */
		Availabe_Trains availabe_Trains = feignClientToTrainApplication.train_search(train_Search_Requestjson);

		/* Connecting to Train Application through WebClient */
//		String url = "http://localhost:1011/train_search";
//		Availabe_Trains availabe_Trains = WebClient.create()
//				 .post()
//				 .uri(url)
//				 .bodyValue(train_Search_Requestjson)
//				 .retrieve()
//				 .bodyToMono(Availabe_Trains.class)
//				 .block();
		
		return availabe_Trains;
	}

}
