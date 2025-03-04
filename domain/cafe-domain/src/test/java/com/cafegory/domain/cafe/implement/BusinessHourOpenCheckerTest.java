package com.cafegory.domain.cafe.implement;

import com.cafegory.domain.cafe.domain.BusinessHour;
import com.cafegory.domain.common.time.FakeTimeUtil;
import com.cafegory.domain.common.time.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static com.cafegory.domain.cafe.testfixtures.builder.BusinessHourBuilder.*;
import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;

class BusinessHourOpenCheckerTest {

    private TimeUtil timeUtil = new FakeTimeUtil();
    private BusinessHourOpenChecker sut = new BusinessHourOpenChecker(timeUtil);

    @ParameterizedTest
    @MethodSource("provideLocalDateTime")
    @DisplayName("영업 중 여부를 판단한다.")
    void determines_if_it_is_during_business_hours(LocalDateTime now) {
        //given
        List<BusinessHour> businessHours = List.of(
                aBusinessHour()
                        .withDayOfWeek(MONDAY)
                        .withOpeningTime(9, 0)
                        .withClosingTime(21, 0)
                        .build()
        );
        //when
        boolean isOpen = sut.checkWithBusinessHours(businessHours, now);
        //then
        assertThat(isOpen).isTrue();
    }

    private static Stream<Arguments> provideLocalDateTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 1, 29, 9, 0, 0)),
                Arguments.of(LocalDateTime.of(2024, 1, 29, 12, 0, 0)),
                Arguments.of(LocalDateTime.of(2024, 1, 29, 20, 59, 59))
        );
    }

    @ParameterizedTest
    @MethodSource("provideLocalDateTime5")
    @DisplayName("영업 종료 여부를 판단한다.")
    void determines_if_it_is_after_business_hours(LocalDateTime now) {
        List<BusinessHour> businessHours = List.of(
                aBusinessHour()
                        .withDayOfWeek(MONDAY)
                        .withOpeningTime(9, 0)
                        .withClosingTime(21, 0)
                        .build()
        );
        //when
        boolean isOpen = sut.checkWithBusinessHours(businessHours, now);
        //then
        assertThat(isOpen).isFalse();
    }

    private static Stream<Arguments> provideLocalDateTime5() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 1, 29, 8, 59, 59)),
                Arguments.of(LocalDateTime.of(2024, 1, 29, 21, 0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideLocalDateTime2")
    @DisplayName("24시간 영업한다.")
    void open_24Hours(LocalDateTime now) {
        List<BusinessHour> businessHours = List.of(
                aBusinessHour()
                        .withOpeningStartOfDay()
                        .withClosingEndOfDay()
                        .withDayOfWeek(MONDAY)
                        .build(),
                aBusinessHour()
                        .withOpeningStartOfDay()
                        .withClosingEndOfDay()
                        .withDayOfWeek(TUESDAY)
                        .build()
        );
        //when
        boolean isOpen = sut.checkWithBusinessHours(businessHours, now);
        //then
        assertThat(isOpen).isTrue();
    }

    private static Stream<Arguments> provideLocalDateTime2() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 1, 29, 23, 59, 59)),
                Arguments.of(LocalDateTime.of(2024, 1, 30, 0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideLocalDateTime3")
    @DisplayName("다음날 새벽까지 영업한다.")
    void open_until_early_morning_the_next_day(LocalDateTime now, boolean expected) {
        List<BusinessHour> businessHours = List.of(
                aBusinessHour()
                        .withOpeningTime(9, 0)
                        .withClosingTime(2, 0)
                        .withDayOfWeek(MONDAY)
                        .build(),
                aBusinessHour()
                        .withOpeningTime(9, 0)
                        .withClosingTime(2, 0)
                        .withDayOfWeek(TUESDAY)
                        .build()
        );
        //when
        boolean isOpen = sut.checkWithBusinessHours(businessHours, now);
        //then
        assertThat(isOpen).isEqualTo(expected);
    }

    private static Stream<Arguments> provideLocalDateTime3() {
        return Stream.of(
                // LocalDateTime now, boolean expected
                Arguments.of(LocalDateTime.of(2024, 1, 29, 23, 59, 59), true),
                Arguments.of(LocalDateTime.of(2024, 1, 30, 0, 0), true),
                Arguments.of(LocalDateTime.of(2024, 1, 30, 1, 59, 59), true),
                Arguments.of(LocalDateTime.of(2024, 1, 30, 2, 0, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideLocalDateTime4")
    @DisplayName("평일은 일찍마감, 금토는 24시간 오픈, 일요일은 일찍 마감")
    void business_hours_are_different_each_day(LocalDateTime now, boolean expected) {
        List<BusinessHour> businessHours = List.of(
                aBusinessHour()
                        .withOpeningTime(9, 0)
                        .withClosingTime(22, 0)
                        .withDayOfWeek(MONDAY)
                        .build(),
                aBusinessHour()
                        .withOpeningTime(9, 0)
                        .withClosingEndOfDay()
                        .withDayOfWeek(FRIDAY)
                        .build(),
                aBusinessHour()
                        .withOpeningStartOfDay()
                        .withClosingEndOfDay()
                        .withDayOfWeek(SATURDAY)
                        .build(),
                aBusinessHour()
                        .withOpeningStartOfDay()
                        .withClosingTime(22, 0)
                        .withDayOfWeek(SUNDAY)
                        .build()
        );
        //when
        boolean isOpen = sut.checkWithBusinessHours(businessHours, now);
        //then
        assertThat(isOpen).isEqualTo(expected);
    }

    private static Stream<Arguments> provideLocalDateTime4() {
        return Stream.of(
			/*
			 29일 : 월
			 2일 : 금
			 3일 : 토
			 4일 : 일
			*/
                Arguments.of(LocalDateTime.of(2024, 1, 29, 21, 59, 59), true),
                Arguments.of(LocalDateTime.of(2024, 1, 29, 22, 0), false),
                Arguments.of(LocalDateTime.of(2024, 2, 2, 23, 59, 58), true),
                Arguments.of(LocalDateTime.of(2024, 2, 2, 23, 59, 59), false),
                Arguments.of(LocalDateTime.of(2024, 2, 3, 0, 0), true),
                Arguments.of(LocalDateTime.of(2024, 2, 4, 0, 0), true),
                Arguments.of(LocalDateTime.of(2024, 2, 4, 21, 59, 59), true),
                Arguments.of(LocalDateTime.of(2024, 2, 4, 22, 0, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideChosenTimeAndExpected")
    @DisplayName("영업시간 시간 사이에 선택된 시간이 포함되는지 확인한다.")
    void check_if_chosen_time_is_within_business_hours(LocalTime chosenStartTime, LocalTime chosenEndTime,
                                                       boolean expected) {
        //given
        LocalTime businessStartTime = LocalTime.of(9, 0);
        LocalTime businessEndTime = LocalTime.of(21, 0);
        //when
        boolean isBetween = sut.checkBetweenBusinessHours(businessStartTime, businessEndTime,
                chosenStartTime, chosenEndTime);
        //then
        assertThat(isBetween).isEqualTo(expected);
    }

    private static Stream<Arguments> provideChosenTimeAndExpected() {
        return Stream.of(
                Arguments.of(
                        LocalTime.of(9, 0),
                        LocalTime.of(21, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(9, 0, 1),
                        LocalTime.of(21, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(9, 0),
                        LocalTime.of(20, 59, 59),
                        true
                ),
                Arguments.of(
                        LocalTime.of(9, 0, 1),
                        LocalTime.of(20, 59, 59),
                        true
                ),
                Arguments.of(
                        LocalTime.of(8, 59, 59),
                        LocalTime.of(21, 0),
                        false
                ),
                Arguments.of(
                        LocalTime.of(9, 0, 0),
                        LocalTime.of(21, 0, 1),
                        false
                ),
                Arguments.of(
                        LocalTime.of(8, 59, 59),
                        LocalTime.of(21, 0, 1),
                        false
                )
        );
    }

    @Test
    @DisplayName("24시간 영업 시 선택된 시간이 포함되는지 확인한다.")
    void check_if_chosen_time_is_within_24hour_business_hours() {
        //given
        LocalTime businessStartTime = LocalTime.of(0, 0);
        LocalTime businessEndTime = timeUtil.maxLocalTime();
        LocalTime chosenStartTime = LocalTime.of(0, 0);
        LocalTime chosenEndTime = timeUtil.maxLocalTime();
        //when
        boolean isBetween = sut.checkBetweenBusinessHours(businessStartTime, businessEndTime,
                chosenStartTime, chosenEndTime);
        //then
        assertThat(isBetween).isTrue();

    }

    @ParameterizedTest
    @MethodSource("provideChosenTimeAndExpected4")
    @DisplayName("영업시간이 다음 날 새벽까지 이어질 경우, 선택된 시간이 포함되는지 확인한다.")
    void check_if_chosen_time_is_within_overnight_business_hours(LocalTime chosenStartTime, LocalTime chosenEndTime,
                                                                 boolean expected) {
        //given
        LocalTime businessStartTime = LocalTime.of(7, 0);
        LocalTime businessEndTime = LocalTime.of(2, 0);
        //when
        boolean isBetween = sut.checkBetweenBusinessHours(businessStartTime, businessEndTime,
                chosenStartTime, chosenEndTime);
        //then
        assertThat(isBetween).isEqualTo(expected);
    }

    private static Stream<Arguments> provideChosenTimeAndExpected4() {
        return Stream.of(
                Arguments.of(
                        LocalTime.of(6, 59, 59),
                        LocalTime.of(8, 0),
                        false
                ),
                Arguments.of(
                        LocalTime.of(7, 0),
                        LocalTime.of(8, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(23, 0),
                        LocalTime.of(23, 59, 59),
                        true
                ),
                Arguments.of(
                        LocalTime.of(23, 0),
                        LocalTime.of(0, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(23, 0),
                        LocalTime.of(2, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(23, 0),
                        LocalTime.of(2, 0, 1),
                        false
                ),
                Arguments.of(
                        LocalTime.of(0, 0),
                        LocalTime.of(1, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(0, 0),
                        LocalTime.of(2, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(0, 0),
                        LocalTime.of(2, 0, 1),
                        false
                ),
                Arguments.of(
                        LocalTime.of(7, 0),
                        LocalTime.of(2, 0),
                        true
                ),
                Arguments.of(
                        LocalTime.of(6, 59, 59),
                        LocalTime.of(2, 0),
                        false
                ),
                Arguments.of(
                        LocalTime.of(7, 0),
                        LocalTime.of(2, 0, 1),
                        false
                )
        );
    }

}
