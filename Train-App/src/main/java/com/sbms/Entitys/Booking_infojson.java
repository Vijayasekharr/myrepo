package com.sbms.Entitys;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class Booking_infojson {
	
	private Integer train_no;
	private String from_station;
	private String to_station;
	private String quota;
	private String date;

}
