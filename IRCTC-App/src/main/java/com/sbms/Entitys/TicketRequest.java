package com.sbms.Entitys;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class TicketRequest {
	
	@NonNull
	private Integer train_no;
	@NonNull
	private String from_station;
	@NonNull
	private String to_station;
	@NonNull
	private String quota;
	@NonNull
	private String date;
	@NonNull
	@Embedded
	private Passenger passenger;
	
}
