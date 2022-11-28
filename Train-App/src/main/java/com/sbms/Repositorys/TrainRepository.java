package com.sbms.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sbms.Entitys.Train;

public interface TrainRepository extends JpaRepository<Train, Integer> {
	
//	Double findByAmount(String from_station, String quota, Integer train_no);
//	Double findByAmount(String quota, Integer train_no,String to_station);
//	
//	String findByArrival(String from_station, String quota, Integer train_no);
//	String findByArrival(String quota, Integer train_no,String to_station);
//	
//	String findByDeparture(String from_station, String quota, Integer train_no);
//	String findByDeparture(String quota, Integer train_no,String to_station);
//	
//	String findByTrain_name(Integer train_no);
	
	@Query("from Train where station=:from_station and quota=:quota and train_no=:train_no")
	Train findTrainAt_from_station(String from_station, String quota, Integer train_no);
	
	@Query("from Train where station=:to_station and quota=:quota and train_no=:train_no")
	Train findTrainAt_to_station(String to_station, String quota, Integer train_no);
	
	@Query(value = "select train_name from Train where station=:station group by train_name",nativeQuery = true)
	List<String> findTrainsAtFromStationWithoutFiltering(String station);
	
	@Query(value = "select train_name from Train where station=:station and train_name in :listString group by train_name",nativeQuery = true)
	List<String> findTrainsToStationWithFiltering(String station, List<String> listString);
	
	@Query(value = "select train_name from Train where station=:station and train_name in :listString group by train_name",nativeQuery = true)
	List<String> findTrainsAtFromStationWithFiltering(String station,List<String> listString);
	
	@Query(value = "select train_no from Train where train_name=:trainname group by train_no",nativeQuery = true)
	Integer findTrainNumber(String trainname);
	
	@Query(value="select quota from Train where train_no=:integer group by quota", nativeQuery = true)
	List<String> findAvailableQuotas(Integer integer);
	
	@Query(value="select id from Train where station=:from_station and quota=:quota and train_no=:train_no", nativeQuery = true)
	Integer getTrainId_At_From_Station(String from_station,String quota, Integer train_no);
	
	@Query(value="select id from Train where station=:to_station and quota=:quota and train_no=:train_no", nativeQuery = true)
	Integer getTrainId_At_to_Station(String to_station, String quota, Integer train_no);
	
	@Query(value="select distinct(station) from train where id BETWEEN :id1 and :id2", nativeQuery = true)
	List<String> getAvailable_Stations_between_Two_Stations(Integer id1, Integer id2);
	
	
	
	
}
