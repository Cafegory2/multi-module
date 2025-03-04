package com.cafegory.domain.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PagedRequest {

	protected int page = 0;
	protected int sizePerPage = 10;

	protected PagedRequest(int page, int sizePerPage) {
		this.page = page;
		this.sizePerPage = sizePerPage;
	}
}
