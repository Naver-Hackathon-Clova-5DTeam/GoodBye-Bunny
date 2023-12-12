package com.clova.hackathon.goodbyebunny.domain.member.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginMemberRequest {
    private String nickname;
    private String password;
}
