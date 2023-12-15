package com.clova.hackathon.goodbyebunny.domain.keyword.dao;


import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Long> {
    Optional<Keyword> findKeywordById(Long keywordId);
    Optional<Keyword> findKeywordByWord(String keywordWord);
}
