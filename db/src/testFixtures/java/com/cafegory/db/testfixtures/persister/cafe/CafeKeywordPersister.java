package com.cafegory.db.testfixtures.persister.cafe;

import com.cafegory.db.cafe.cafe.CafeEntity;
import com.cafegory.db.cafe.keyword.CafeKeywordEntity;
import com.cafegory.db.cafe.keyword.CafeKeywordJpaRepository;

public class CafeKeywordPersister {

	private String keyword = "테스트 키워드";
	private CafeEntity cafe;

	private CafeKeywordPersister() {
	}

	private CafeKeywordPersister(CafeKeywordPersister copy) {
		this.keyword = copy.keyword;
		this.cafe = copy.cafe;
	}

	public CafeKeywordPersister but() {
		return new CafeKeywordPersister(this);
	}

	public static CafeKeywordPersister aCafeKeyword() {
		return new CafeKeywordPersister();
	}

	public CafeKeywordPersister withKeyword(String keyword) {
		this.keyword = keyword;
		return this;
	}

	public CafeKeywordPersister withCafe(CafeEntity cafe) {
		this.cafe = cafe;
		return this;
	}

	public CafeKeywordEntity build() {
		return CafeKeywordEntity.builder()
			.keyword(this.keyword)
			.cafe(this.cafe)
			.build();
	}

	public static class CafeKeywordRepoHolder {
		private static CafeKeywordJpaRepository keywordRepository;

		public static void init(CafeKeywordJpaRepository keywordRepo) {
			keywordRepository = keywordRepo;
		}
	}

	public CafeKeywordEntity persist() {
		return CafeKeywordRepoHolder.keywordRepository.save(build());
	}
}
