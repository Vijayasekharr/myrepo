package com.sbms.ServicesI;

import java.util.List;
import java.util.Optional;

import com.sbms.Entitys.Train;
import com.sbms.Entitys.Train_Search_Request;
import com.sbms.Entitys.Train_Search_Responsejson;

public interface TrainServiceI {
	
	public Train save(Train trian);
	
	public List<Train> findAll();
	
	public List<Train> saveAll(List<Train> trainList);
	
	public Optional<Train> findById(Integer id);
	
	public List<Train_Search_Responsejson> trains_Search(Train_Search_Request train_Search_Request);

}
