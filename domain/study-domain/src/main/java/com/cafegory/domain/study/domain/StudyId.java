package com.cafegory.domain.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyId {

	private final Long id;

	public boolean isSameId(StudyId id) {
		return this.id.equals(id.getId());
	}
}
