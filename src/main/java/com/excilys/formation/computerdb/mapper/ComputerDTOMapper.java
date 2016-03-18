package com.excilys.formation.computerdb.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.dto.ComputerDTO;
import com.excilys.formation.computerdb.model.Computer;

public class ComputerDTOMapper {
	
	public static ComputerDTO map(Computer c){
		return new ComputerDTO(c);
	}
	
	public static List<ComputerDTO> map(List<Computer> computers){
		List<ComputerDTO> list = new ArrayList<>();
		for (Computer c : computers) {
			list.add(new ComputerDTO(c));
		}
		return list;
	}
}
