package com.cafegory.domain.cafe.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {

	private String fullAddress;
	private String region;
}
