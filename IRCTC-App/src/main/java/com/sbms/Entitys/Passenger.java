package com.sbms.Entitys;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
	
	private String name;
	private String gender;
	private Integer age;
	
	@Embedded
	private Address address;
	
	

}
