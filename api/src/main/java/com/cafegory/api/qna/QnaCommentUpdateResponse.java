package com.cafegory.api.qna;

import com.cafegory.domain.qna.domain.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentUpdateResponse {

	private Long commentId;
	private String content;

	private QnaCommentUpdateResponse(Long commentId, String content) {
		this.commentId = commentId;
		this.content = content;
	}

	public static QnaCommentUpdateResponse from(Comment comment) {
		return new QnaCommentUpdateResponse(comment.getId().getId(), comment.getContent());
	}
}
