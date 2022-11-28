package com.sbms.Entitys;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@XmlRootElement
@Entity
@NoArgsConstructor
public class Train implements Serializable {
	
	@SequenceGenerator(name="logical_name", sequenceName = "train_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logical_name")
	@Id
	private Integer id;
	private Integer train_no;
	private String train_name;
	private String station;
	private String quota;
	private Double amount;
	private LocalTime arrival;
	private LocalTime departure;
	private Integer day;
	private Integer km;
}
