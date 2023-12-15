package com.clova.hackathon.goodbyebunny.domain.member.dao;

import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByNickname(String nickname);

}
