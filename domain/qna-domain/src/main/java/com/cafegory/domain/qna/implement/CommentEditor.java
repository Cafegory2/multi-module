package com.cafegory.domain.qna.implement;

import com.cafegory.domain.member.domain.MemberId;
import com.cafegory.domain.qna.domain.CommentContent;
import com.cafegory.domain.qna.domain.CommentId;
import com.cafegory.domain.qna.domain.ParentCommentId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.cafegory.domain.qna.repository.CommentRepository;
import com.cafegory.domain.study.domain.StudyId;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentEditor {

	private final CommentRepository commentRepository;
	private final CommentValidator commentValidator;

	public CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository.saveRootComment(content, studyId, memberId);
	}

	public CommentId saveSubComment(
            CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository.saveSubComment(content, parentCommentId, studyId, memberId);
	}

	public void edit(CommentContent content, CommentId commentId) {
		commentValidator.validateContentNotBlank(content.getContent());
		commentRepository.edit(content, commentId);
	}

	public void remove(CommentId commentId, LocalDateTime now) {
		commentRepository.remove(commentId, now);
	}
}
