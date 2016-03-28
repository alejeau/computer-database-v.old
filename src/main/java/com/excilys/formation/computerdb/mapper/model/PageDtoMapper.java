package com.excilys.formation.computerdb.mapper.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.excilys.formation.computerdb.dto.ComputerDto;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.pagination.SortedPage;

public class PageDtoMapper {

	public static ComputerDto toDTO(Computer c) {
		return new ComputerDto(c);
	}

	public static List<ComputerDto> toDTO(List<Computer> computers) {
		List<ComputerDto> list = new ArrayList<>();
		for (Computer c : computers) {
			list.add(new ComputerDto(c));
		}
		return list;
	}
	
	public static Page<ComputerDto> toPageDto(Page<Computer> p) {
		List<Computer> cList = p.getPage();
		List<ComputerDto> dtoList = new ArrayList<>();
		Page<ComputerDto> sp = new Page<>(new LinkedList<>(), p.getCurrentPageNumber(), p.getMaxPageNumber(), p.getObjectsPerPages(), p.getNbEntries());
		
		for (Computer c : cList) {
			dtoList.add(new ComputerDto(c));
		}
		
		sp.setPage(dtoList);
		
		return sp;
	}
	
	public static SortedPage<ComputerDto> toSortedPageDto(SortedPage<Computer> p) {
		List<Computer> cList = p.getPage();
		List<ComputerDto> dtoList = new ArrayList<>();
		SortedPage<ComputerDto> sp = new SortedPage<>(new LinkedList<>(), p.getCurrentPageNumber(), p.getMaxPageNumber(), p.getObjectsPerPages(), p.getNbEntries(), p.getField(), p.isAscending());
		
		for (Computer c : cList) {
			dtoList.add(new ComputerDto(c));
		}
		
		sp.setPage(dtoList);
		
		return sp;
	}
	
	public static SearchPage<ComputerDto> toSearchPageDto(SearchPage<Computer> p) {
		List<Computer> cList = p.getPage();
		List<ComputerDto> dtoList = new ArrayList<>();
		SearchPage<ComputerDto> sp = new SearchPage<>(new LinkedList<>(), p.getCurrentPageNumber(), p.getMaxPageNumber(), p.getObjectsPerPages(), p.getNbEntries(), p.getField(), p.isAscending(), p.getSearch());
		
		for (Computer c : cList) {
			dtoList.add(new ComputerDto(c));
		}
		
		sp.setPage(dtoList);
		
		return sp;
	}
}
