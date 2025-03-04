package com.cafegory.domain.common.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TimeUtil {

	LocalTime maxLocalTime();

	LocalDateTime now();

	LocalDate localDate(int year, int month, int day);

	LocalTime localTime(int hour, int minute, int second);

	LocalDateTime localDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second);

	LocalDateTime minLocalDateTime(LocalDateTime now);

	LocalDateTime maxLocalDateTime(LocalDateTime now);
}
