package com.cafegory.db.testfixtures.persister.cafe;

import com.cafegory.db.cafe.tag.CafeTagEntity;
import com.cafegory.db.cafe.tag.CafeTagJpaRepository;
import com.cafegory.domain.cafe.domain.CafeTagType;

public class CafeTagPersister {

	private CafeTagType type = CafeTagType.WIFI;

	private CafeTagPersister() {
	}

	private CafeTagPersister(CafeTagPersister copy) {
		this.type = copy.type;
	}

	public CafeTagPersister but() {
		return new CafeTagPersister(this);
	}

	public static CafeTagPersister aCafeTag() {
		return new CafeTagPersister();
	}

	public CafeTagPersister withType(CafeTagType type) {
		this.type = type;
		return this;
	}

	public CafeTagEntity build() {
		return CafeTagEntity.builder()
			.type(this.type)
			.build();
	}

	public static class CafeTagRepoHolder {
		private static CafeTagJpaRepository cafeTagJpaRepository;

		public static void init(CafeTagJpaRepository cafeTagRepo) {
			cafeTagJpaRepository = cafeTagRepo;
		}
	}

	public CafeTagEntity persist() {
		return CafeTagRepoHolder.cafeTagJpaRepository.save(build());
	}
}
