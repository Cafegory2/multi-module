package com.cafegory.domain.study.implement;

import com.cafegory.domain.common.time.TimeUtil;
import com.cafegory.domain.common.exception.CafegoryException;
import com.cafegory.domain.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.cafegory.domain.study.domain.Coordinator;
import com.cafegory.domain.study.domain.Schedule;
import com.cafegory.domain.study.domain.Study;
import com.cafegory.domain.study.domain.StudyMemberId;

import java.time.LocalDateTime;
import java.util.List;

import static com.cafegory.domain.common.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class StudyValidator {

	public static final int MAX_MEMBER_CAPACITY = 6;
	public static final int MIN_MEMBER_CAPACITY = 2;
	private static final int MAX_STUDY_NAME_LENGTH = 20;
	public static final int MIN_DELAY_BEFORE_START = 1 * 60 * 60;

	private final TimeUtil timeUtil;

	public void validateEmptyOrWhiteSpace(String target, ExceptionType exceptionType) {
		if (target.isBlank()) {
			throw new CafegoryException(exceptionType);
		}
	}

	public void validateNameLength(String name) {
		if (name.isEmpty() || name.length() > MAX_STUDY_NAME_LENGTH) {
			throw new CafegoryException(CAFE_STUDY_INVALID_NAME);
		}
	}

	public void validateStartDateTime(LocalDateTime now, LocalDateTime startDateTime) {
		LocalDateTime nowPlusHour = now.plusSeconds(MIN_DELAY_BEFORE_START - 1);

		if (!startDateTime.isAfter(nowPlusHour)) {
			throw new CafegoryException(STUDY_ONCE_WRONG_START_TIME);
		}
	}

	public void validateStartDate(LocalDateTime startDateTime) {
		LocalDateTime plusMonths = timeUtil.now().plusMonths(1);
		if (startDateTime.isAfter(plusMonths)) {
			throw new CafegoryException(CAFE_STUDY_WRONG_START_DATE);
		}
	}

	public void validateMaxParticipants(int maxParticipants) {
		if (maxParticipants > MAX_MEMBER_CAPACITY || maxParticipants < MIN_MEMBER_CAPACITY) {
			throw new CafegoryException(STUDY_ONCE_LIMIT_MEMBER_CAPACITY);
		}
	}

	public void validateStudyScheduleOverlap(Schedule newSchedule, List<Study> participantStudies) {
		boolean isOverlapped = participantStudies.stream()
			.map(study -> study.getContent().getSchedule())
			.anyMatch(schedule -> schedule.overlaps(newSchedule));

		if (isOverlapped) {
			throw new CafegoryException(STUDY_ONCE_CONFLICT_TIME);
		}
	}

	public void validateCoordinatorIsInStudy(Study study, List<Coordinator> coordinators) {
		boolean isCoordinator = coordinators.stream()
			.anyMatch(study::isManagedBy);

		if (!isCoordinator) {
			throw new CafegoryException(CAFE_STUDY_INVALID_LEADER);
		}
	}

	public void validateStudyMemberOnlyOne(List<StudyMemberId> participantsIds) {
		if (participantsIds.size() > 1) {
			throw new CafegoryException(CAFE_STUDY_DELETE_FAIL_MEMBERS_PRESENT);
		}
	}
}
