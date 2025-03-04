package com.cafegory.domain.member.domain;

import com.cafegory.domain.common.DateAudit;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

	private MemberId id;
	private MemberContent content;
	private Role role;
	private String email;
	private String bio;
	private BeverageSize beverageSize;
	private DateAudit dateAudit;
	private String refreshToken;

	public boolean hasId(MemberId id) {
		return this.id.isSameId(id);
	}
}
