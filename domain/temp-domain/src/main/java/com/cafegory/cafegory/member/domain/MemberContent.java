package com.cafegory.cafegory.member.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberContent {

    private String nickname;
    private String imgUrl;
}
