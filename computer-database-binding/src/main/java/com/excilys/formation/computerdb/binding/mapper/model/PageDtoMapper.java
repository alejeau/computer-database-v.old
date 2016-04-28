package com.excilys.formation.computerdb.mapper.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.excilys.formation.computerdb.dto.model.ComputerDto;
import com.excilys.formation.computerdb.dto.page.SearchPageDto;
import com.excilys.formation.computerdb.dto.page.SortedPageDto;
import com.excilys.formation.computerdb.model.Computer;
import com.excilys.formation.computerdb.model.pagination.Page;
import com.excilys.formation.computerdb.model.pagination.SearchPage;
import com.excilys.formation.computerdb.model.pagination.SortedPage;

public class PageDtoMapper {

	public static ComputerDto toDTO(Computer c, String locale) {
		return new ComputerDto(c, locale);
	}

	public static List<ComputerDto> toDTO(List<Computer> computers, String locale) {
		List<ComputerDto> list = new ArrayList<>();
		for (Computer c : computers) {
			list.add(new ComputerDto(c, locale));
		}
		return list;
	}

	public static Page<ComputerDto> toPageDto(Page<Computer> p, String locale) {
		List<Computer> cList = p.getPage();
		List<ComputerDto> dtoList = new ArrayList<>();
		Page<ComputerDto> sp = new Page<>(new LinkedList<>(), p.getCurrentPageNumber(), p.getMaxPageNumber(),
				p.getObjectsPerPages(), p.getNbEntries());

		for (Computer c : cList) {
			dtoList.add(new ComputerDto(c, locale));
		}

		sp.setPage(dtoList);

		return sp;
	}

	public static SortedPageDto<ComputerDto> toSortedPageDto(SortedPage<Computer> p, String locale) {
		List<Computer> cList = p.getPage();
		List<ComputerDto> dtoList = new ArrayList<>();
		SortedPageDto<ComputerDto> spdto = new SortedPageDto<>(new LinkedList<>(), p.getCurrentPageNumber(),
				p.getMaxPageNumber(), p.getObjectsPerPages(), p.getNbEntries(), p.getField().toString(),
				p.isAscending());

		for (Computer c : cList) {
			dtoList.add(new ComputerDto(c, locale));
		}

		spdto.setPage(dtoList);

		return spdto;
	}

	public static SearchPageDto<ComputerDto> toSearchPageDto(SearchPage<Computer> p, String locale) {
		List<Computer> cList = p.getPage();
		List<ComputerDto> dtoList = new ArrayList<>();
		SearchPageDto<ComputerDto> spdto = new SearchPageDto<>(new LinkedList<>(), p.getCurrentPageNumber(),
				p.getMaxPageNumber(), p.getObjectsPerPages(), p.getNbEntries(), p.getField().toString(),
				p.isAscending(), p.getSearch());

		for (Computer c : cList) {
			dtoList.add(new ComputerDto(c, locale));
		}

		spdto.setPage(dtoList);

		return spdto;
	}
}
