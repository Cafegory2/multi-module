package com.cafegory.db.study.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudyJpaRepository extends JpaRepository<CafeStudyEntity, Long> {

	List<CafeStudyEntity> findByMember_Id(Long memberId);

	// TODO: 카공 생성 시 tag 삽입 기능 구현 하면 tag fetch join으로 가져오기
	@Query(value = "select s from CafeStudyEntity s" +
		" inner join fetch s.member" +
		" where s.id = :studyId")
	Optional<CafeStudyEntity> findWithMember(@Param("studyId") Long studyId);

	@Query(value = "select s from CafeStudyEntity s" +
		" inner join fetch s.member")
	List<CafeStudyEntity> findAllByCafeId(Long cafeId);

	@Query("SELECT c FROM CafeStudyEntity c"
		+ " inner join fetch c.member"
		+ " WHERE c.id IN :studyIds AND c.studyPeriod.startDateTime >= :now")
	List<CafeStudyEntity> findUpcomingsWithMemberBy(@Param("studyIds") List<Long> studyIds,
		@Param("now") LocalDateTime now);

	@Query("SELECT c.views FROM CafeStudyEntity c WHERE c.id = :id")
	int findViewsById(@Param("id") Long id);
}
