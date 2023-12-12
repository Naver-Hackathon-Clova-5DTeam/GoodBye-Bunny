package com.clova.hackathon.goodbyebunny.domain.member.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpMemberRequest {

    private String nickname;
    private int age;
    private String password;
}
