package com.cafegory.db.testfixtures.persister.qna;

import com.cafegory.db.member.MemberEntity;
import com.cafegory.db.qna.CafeStudyCommentEntity;
import com.cafegory.db.qna.CommentJpaRepository;
import com.cafegory.db.study.study.CafeStudyEntity;
import com.cafegory.domain.study.domain.StudyRole;

public class CommentPersister {

	private MemberEntity author;
	private StudyRole studyRole = StudyRole.MEMBER;
	private String content = "테스트 댓글 내용";
	private CafeStudyCommentEntity parentComment;
	private CafeStudyEntity cafeStudy;

	private CommentPersister() {
	}

	private CommentPersister(CommentPersister copy) {
		this.author = copy.author;
		this.studyRole = copy.studyRole;
		this.content = copy.content;
		this.parentComment = copy.parentComment;
		this.cafeStudy = copy.cafeStudy;
	}

	public CommentPersister but() {
		return new CommentPersister(this);
	}

	public static CommentPersister aComment() {
		return new CommentPersister();
	}

	public CommentPersister withAuthor(MemberEntity author) {
		this.author = author;
		return this;
	}

	public CommentPersister withCoordinator(MemberEntity coordinator) {
		this.author = coordinator;
		this.studyRole = StudyRole.COORDINATOR;
		return this;
	}

	public CommentPersister withMember(MemberEntity author) {
		this.author = author;
		this.studyRole = StudyRole.MEMBER;
		return this;
	}

	public CommentPersister withStudyRole(StudyRole studyRole) {
		this.studyRole = studyRole;
		return this;
	}

	public CommentPersister withContent(String content) {
		this.content = content;
		return this;
	}

	public CommentPersister replyTo(CafeStudyCommentEntity parentComment) {
		this.parentComment = parentComment;
		return this;
	}

	public CommentPersister withStudy(CafeStudyEntity cafeStudy) {
		this.cafeStudy = cafeStudy;
		return this;
	}

	public CafeStudyCommentEntity build() {
		return CafeStudyCommentEntity.builder()
			.author(this.author)
			.studyRole(this.studyRole)
			.content(this.content)
			.parentComment(this.parentComment)
			.cafeStudy(this.cafeStudy)
			.build();
	}

	public static class CommentRepoHolder {
		private static CommentJpaRepository commentRepository;

		public static void init(CommentJpaRepository commentRepo) {
			commentRepository = commentRepo;
		}
	}

	public CafeStudyCommentEntity persist() {
		return CommentRepoHolder.commentRepository.save(build());
	}
}
