package com.cafegory.db.study.studymember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberJpaRepository extends JpaRepository<CafeStudyMemberEntity, Long> {

	List<CafeStudyMemberEntity> findByMember_Id(Long memberId);

	List<CafeStudyMemberEntity> findByCafeStudy_Id(Long studyId);

	int countByCafeStudy_Id(Long cafeStudyId);

	Optional<CafeStudyMemberEntity> findByCafeStudy_IdAndMember_Id(Long studyId, Long memberId);
}
