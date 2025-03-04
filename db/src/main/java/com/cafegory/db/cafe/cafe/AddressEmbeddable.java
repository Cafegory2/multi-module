package com.cafegory.db.cafe.cafe;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class AddressEmbeddable {

	private String fullAddress;
	private String region;

	public AddressEmbeddable() {
	}

	public AddressEmbeddable(final String fullAddress, final String region) {
		this.fullAddress = fullAddress;
		this.region = region;
	}
}
