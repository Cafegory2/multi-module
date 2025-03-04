package com.cafegory.db.testfixtures.persister.cafe;

import com.cafegory.db.cafe.businessHour.BusinessHourEntity;
import com.cafegory.db.cafe.businessHour.BusinessHourJpaRepository;
import com.cafegory.db.cafe.cafe.CafeEntity;
import com.cafegory.domain.common.time.FakeTimeUtil;
import com.cafegory.domain.common.time.TimeUtil;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class BusinessHourPersister {

	private TimeUtil timeUtil = new FakeTimeUtil();

	private DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
	private LocalTime openingTime = timeUtil.localTime(9, 0, 0);
	private LocalTime closingTime = timeUtil.localTime(21, 0, 0);
	private CafeEntity cafe;

	private BusinessHourPersister() {
	}

	private BusinessHourPersister(BusinessHourPersister copy) {
		this.dayOfWeek = copy.dayOfWeek;
		this.openingTime = copy.openingTime;
		this.closingTime = copy.closingTime;
		this.cafe = copy.cafe;
	}

	public BusinessHourPersister but() {
		return new BusinessHourPersister(this);
	}

	public static BusinessHourPersister aBusinessHour() {
		return new BusinessHourPersister();
	}

	public BusinessHourPersister withDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		return this;
	}

	public BusinessHourPersister withOpeningTime(int hour, int minute) {
		this.openingTime = LocalTime.of(hour, minute);
		return this;
	}

	public BusinessHourPersister withClosingTime(int hour, int minute) {
		this.closingTime = LocalTime.of(hour, minute);
		return this;
	}

	public BusinessHourPersister withOpeningStartOfDay() {
		this.openingTime = timeUtil.localTime(0, 0, 0);
		return this;
	}

	public BusinessHourPersister withClosingEndOfDay() {
		this.closingTime = timeUtil.maxLocalTime();
		return this;
	}

	public BusinessHourPersister withCafe(CafeEntity cafe) {
		this.cafe = cafe;
		return this;
	}

	public BusinessHourEntity build() {
		return BusinessHourEntity.builder()
			.dayOfWeek(this.dayOfWeek)
			.openingTime(this.openingTime)
			.closingTime(this.closingTime)
			.cafe(this.cafe)
			.build();
	}

	public static class BusinessHourRepoHolder {
		private static BusinessHourJpaRepository businessHourRepository;

		public static void init(BusinessHourJpaRepository businessHOurRepo) {
			businessHourRepository = businessHOurRepo;
		}
	}

	public BusinessHourEntity persist() {
		return BusinessHourRepoHolder.businessHourRepository.save(build());
	}
}
