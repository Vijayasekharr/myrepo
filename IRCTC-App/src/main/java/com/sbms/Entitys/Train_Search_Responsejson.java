package com.sbms.Entitys;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Train_Search_Responsejson {

	private Integer train_no;
	private String train_name;
	private String from_date;
	private String arri_at_from_station;
	private String dept_at_from_station;
	private String to_date;
	private String arri_at_to_station;
	private String dept_at_to_station;
	private String total_journey_hours;
	private String total_distance;
	@XmlAttribute
	private String provider="IRCTC";
	@Embedded
	private Quota cost_for_each_Quota;
	
}
