package com.sbms.Entitys;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@XmlRootElement
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationsList {

	List<String> stationsList;

}
