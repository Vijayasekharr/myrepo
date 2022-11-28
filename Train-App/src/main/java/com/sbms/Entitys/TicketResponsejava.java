package com.sbms.Entitys;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@Component
public class TicketResponsejava {
	
	@NonNull
	private Integer train_no;
	@NonNull
	private String train_name;
	@NonNull
	private String from_station;
	@NonNull
	private LocalDate from_date;
	@NonNull
	private LocalTime arri_at_from_station;
	@NonNull
	private LocalTime dept_at_from_station;
	@NonNull
	private String to_station;
	@NonNull
	private LocalTime arri_at_to_station;
	@NonNull
	private LocalTime dept_at_to_station;
	@NonNull
	private LocalDate to_date;
	@NonNull
	private String total_journey_hours;
	@NonNull
	private String total_distance;
	@NonNull
	private Double cost;
	@NonNull
	private String quota;
	
}
