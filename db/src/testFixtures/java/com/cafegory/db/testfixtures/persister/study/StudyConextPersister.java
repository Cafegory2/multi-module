package com.cafegory.db.testfixtures.persister.study;


import com.cafegory.db.cafe.cafe.CafeEntity;
import com.cafegory.db.member.MemberEntity;
import com.cafegory.db.study.study.CafeStudyEntity;
import com.cafegory.db.study.study.StudyJpaRepository;
import com.cafegory.db.study.study.StudyPeriod;
import com.cafegory.db.study.tag.CafeStudyTagEntity;
import com.cafegory.domain.study.domain.MemberComms;
import com.cafegory.domain.study.domain.RecruitmentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cafegory.db.testfixtures.persister.study.StudyStudyTagBuilder.aStudyStudyTag;

public class StudyConextPersister {

	private String name = "테스트 카공 이름";
	private CafeEntity cafe;
	private MemberEntity coordinator;
	private StudyPeriod studyPeriod = StudyPeriod.builder()
		.startDateTime(LocalDateTime.of(2999, 1, 1, 10, 0))
		.endDateTime(LocalDateTime.of(2999, 1, 1, 12, 0)).build();
	private MemberComms memberComms = MemberComms.WELCOME;
	private int maxParticipants = 6;
	private String introduction = "테스트 카공 소개글";
	private RecruitmentStatus recruitmentStatus = RecruitmentStatus.OPEN;

	private List<CafeStudyTagEntity> studyTags = new ArrayList<>();

	private StudyConextPersister() {
	}

	private StudyConextPersister(StudyConextPersister copy) {
		this.name = copy.name;
		this.cafe = copy.cafe;
		this.coordinator = copy.coordinator;
		this.studyPeriod = copy.studyPeriod;
		this.memberComms = copy.memberComms;
		this.maxParticipants = copy.maxParticipants;
		this.introduction = copy.introduction;
		this.studyTags = copy.studyTags;
		this.recruitmentStatus = copy.recruitmentStatus;
	}

	public StudyConextPersister but() {
		return new StudyConextPersister(this);
	}

	public static StudyConextPersister aStudy() {
		return new StudyConextPersister();
	}

	public StudyConextPersister withName(String name) {
		this.name = name;
		return this;
	}

	public StudyConextPersister withCafe(CafeEntity cafeEntity) {
		this.cafe = cafeEntity;
		return this;
	}

	public StudyConextPersister withMember(MemberEntity memberEntity) {
		this.coordinator = memberEntity;
		return this;
	}

	public StudyConextPersister withStudyPeriod(LocalDateTime start, LocalDateTime end) {
		this.studyPeriod = StudyPeriod.builder().startDateTime(start).endDateTime(end).build();
		return this;
	}

	public StudyConextPersister close() {
		this.recruitmentStatus = RecruitmentStatus.CLOSED;
		return this;
	}

	public StudyConextPersister shiftDays(int days) {
		LocalDateTime start = this.studyPeriod.getStartDateTime().plusDays(1);
		LocalDateTime end = this.studyPeriod.getEndDateTime().plusDays(1);

		this.studyPeriod = StudyPeriod.builder().startDateTime(start).endDateTime(end).build();
		return this;
	}

	public StudyConextPersister withStudyPeriodFrom10To12() {
		LocalDateTime start = LocalDateTime.of(2000, 1, 1, 10, 0, 0);
		LocalDateTime end = LocalDateTime.of(2000, 1, 1, 12, 0, 0);

		this.studyPeriod = StudyPeriod.builder().startDateTime(start).endDateTime(end).build();
		return this;
	}

	public StudyConextPersister withMemberComms(MemberComms memberComms) {
		this.memberComms = memberComms;
		return this;
	}

	public StudyConextPersister withMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
		return this;
	}

	public StudyConextPersister withIntroduction(String introduction) {
		this.introduction = introduction;
		return this;
	}

	public StudyConextPersister withRecruitmentStatus(RecruitmentStatus recruitmentStatus) {
		this.recruitmentStatus = recruitmentStatus;
		return this;
	}

	public StudyConextPersister markAsClosed() {
		this.recruitmentStatus = RecruitmentStatus.CLOSED;
		return this;
	}

	public StudyConextPersister includeTags(CafeStudyTagEntity... studyTags) {
		this.studyTags.addAll(List.of(studyTags));
		return this;
	}

	public CafeStudyEntity build() {
		CafeStudyEntity study = CafeStudyEntity.builder()
			.name(this.name)
			.cafe(this.cafe)
			.member(this.coordinator)
			.studyPeriod(this.studyPeriod)
			.memberComms(this.memberComms)
			.maxParticipants(this.maxParticipants)
			.introduction(this.introduction)
			.build();

		study.setRecruitmentStatus(this.recruitmentStatus);
		return study;
	}

	public static class StudyRepoHolder {
		private static StudyJpaRepository studyRepository;

		public static void init(StudyJpaRepository studyRepo) {
			studyRepository = studyRepo;
		}
	}

	public CafeStudyEntity persist() {
		CafeStudyEntity study = StudyRepoHolder.studyRepository.save(build());
		studyTags.forEach(studyTag -> aStudyStudyTag().withStudy(study).withTag(studyTag).persist());

		return study;
	}
}
