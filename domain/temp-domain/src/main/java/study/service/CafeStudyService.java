package study.service;

import cafe.domain.BusinessHour;
import cafe.domain.CafeId;
import cafe.implement.BusinessHourReader;
import cafe.implement.BusinessHourValidator;
import lombok.RequiredArgsConstructor;
import member.domain.MemberId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.domain.*;
import study.implement.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeStudyService {
	private final StudyValidator studyValidator;
	private final BusinessHourValidator businessHourValidator;
	private final BusinessHourReader businessHourReader;
	private final StudyReader studyReader;
	private final StudyEditor studyEditor;
	private final StudyMemberReader studyMemberReader;
	private final StudyMemberEditor studyMemberEditor;

	@Transactional
	public StudyId createStudy(MemberId memberId, LocalDateTime now, StudyContent content, CafeId cafeId) {
		validateStudyCreation(now, content.getSchedule().getStartDateTime());

		List<Study> participantStudies = studyReader.readUpcomingBy(memberId, now);
		studyValidator.validateStudyScheduleOverlap(content.getSchedule(), participantStudies);

		BusinessHour businessHour = businessHourReader.readBy(cafeId, content.getStartDate());
		businessHourValidator.validateBetweenBusinessHour(content.getSchedule(), businessHour);

		StudyId savedStudyId = studyEditor.saveWithCascade(content, cafeId, memberId);
		studyMemberEditor.save(memberId, savedStudyId, StudyRole.COORDINATOR);

		return savedStudyId;
	}

	public void deleteStudy(MemberId memberId, StudyId studyId, LocalDateTime now) {
		List<StudyMemberId> participantIds = studyMemberReader.readParticipantIdsBy(studyId);
		studyValidator.validateStudyMemberOnlyOne(participantIds);

		studyEditor.removeWithCascade(studyId, memberId, now);
	}

	private void validateStudyCreation(LocalDateTime now, LocalDateTime startDateTime) {
		studyValidator.validateStartDateTime(now, startDateTime);
		studyValidator.validateStartDate(startDateTime);
	}
}
