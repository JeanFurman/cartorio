package com.escriba.cartorio.util;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PaginasUtil<T> {
	
	private PaginasUtil() {
		
	}
	
	public static <T> Page<T> fazPaginas(int pageNumber, List<T> lista){
		PagedListHolder<T> pageHolder = new PagedListHolder<>(lista);
		pageHolder.setPageSize(10);
		pageHolder.setPage(pageNumber);
		
        List<T> elements = pageHolder.getPageList();
        int page = pageHolder.getPage();
        int pageSize = pageHolder.getPageSize();
        long totalElements = pageHolder.getNrOfElements();

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return new PageImpl<>(elements, pageRequest, totalElements);
	}

}
