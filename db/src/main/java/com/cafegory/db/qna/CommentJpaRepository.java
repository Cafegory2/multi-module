package com.cafegory.db.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CafeStudyCommentEntity, Long> {

	@Query(value = "select c from CafeStudyCommentEntity c"
		+ " left join fetch c.parentComment"
		+ " inner join fetch c.author"
		+ " where c.cafeStudy.id = :cafeStudyId"
		+ " order by c.id asc")
	List<CafeStudyCommentEntity> findAllBy(@Param("cafeStudyId") Long cafeStudyId);

	boolean existsByParentComment_Id(Long parentCommentId);

	@Query(value = "select c from CafeStudyCommentEntity c" +
		" inner join fetch c.author" +
		" where c.id = :cafeStudyCommentId")
	Optional<CafeStudyCommentEntity> findWithMember(@Param("cafeStudyCommentId") Long commentId);
}