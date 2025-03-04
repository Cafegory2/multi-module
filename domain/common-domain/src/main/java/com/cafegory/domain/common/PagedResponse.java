package com.cafegory.domain.common;

import lombok.Data;

import java.util.List;

@Data
public class PagedResponse<T> {
	private final int nowPage;
	private final int maxPage;
	private final int pageSize;
	private final long totalElements;
	private final List<T> list;

	public static <T> PagedResponse<T> createWithFirstPageAsOne(int nowPage, int maxPage, int pageSize,
		long totalElements, List<T> results) {
		return new PagedResponse<>(nowPage + 1, maxPage, pageSize, totalElements, results);
	}
}
