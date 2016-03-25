package com.excilys.formation.computerdb.mapper.model;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdb.dto.ComputerDTO;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.pagination.Page;
import com.excilys.formation.computerdb.pagination.SearchPage;
import com.excilys.formation.computerdb.pagination.SortedPage;

public class PageDtoMapper {

	public static ComputerDTO toDTO(Computer c) {
		return new ComputerDTO(c);
	}

	public static List<ComputerDTO> toDTO(List<Computer> computers) {
		List<ComputerDTO> list = new ArrayList<>();
		for (Computer c : computers) {
			list.add(new ComputerDTO(c));
		}
		return list;
	}
	
	public static Page<ComputerDTO> toPageDto(Page<Computer> p) {
		List<Computer> cList = p.getPage();
		List<ComputerDTO> dtoList = new ArrayList<>();
		Page<ComputerDTO> sp = new Page<>(null, p.getCurrentPageNumber(), p.getMaxPageNumber(), p.getNbEntries());
		
		for (Computer c : cList) {
			dtoList.add(new ComputerDTO(c));
		}
		
		sp.setPage(dtoList);
		
		return sp;
	}
	
	public static SortedPage<ComputerDTO> toSortedPageDto(SortedPage<Computer> p) {
		List<Computer> cList = p.getPage();
		List<ComputerDTO> dtoList = new ArrayList<>();
		SortedPage<ComputerDTO> sp = new SortedPage<>(null, p.getCurrentPageNumber(), p.getMaxPageNumber(), p.getNbEntries(), p.getField(), p.isAscending());
		
		for (Computer c : cList) {
			dtoList.add(new ComputerDTO(c));
		}
		
		sp.setPage(dtoList);
		
		return sp;
	}
	
	public static SearchPage<ComputerDTO> toSearchPageDto(SearchPage<Computer> p) {
		List<Computer> cList = p.getPage();
		List<ComputerDTO> dtoList = new ArrayList<>();
		SearchPage<ComputerDTO> sp = new SearchPage<>(null, p.getCurrentPageNumber(), p.getMaxPageNumber(), p.getNbEntries(), p.getField(), p.isAscending(), p.getSearch());
		
		for (Computer c : cList) {
			dtoList.add(new ComputerDTO(c));
		}
		
		sp.setPage(dtoList);
		
		return sp;
	}
}
