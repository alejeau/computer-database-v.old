package com.excilys.formation.computerdb.mapper.model;

import java.util.LinkedList;
import java.util.List;

import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.model.Computer;

public class ComputerDtoMapper {
	
	public static List<ComputerDto> toDtoList(List<Computer> list) {
		List<ComputerDto> dtoList = new LinkedList<>();
		
		for (Computer c : list) {
			dtoList.add(new ComputerDto(c));
		}
		
		return dtoList;
	}

}
