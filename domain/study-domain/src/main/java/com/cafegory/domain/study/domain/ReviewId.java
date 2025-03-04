package com.cafegory.domain.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewId {

	private final Long id;

	public boolean isSameId(ReviewId id) {
		return this.id.equals(id.getId());
	}
}
