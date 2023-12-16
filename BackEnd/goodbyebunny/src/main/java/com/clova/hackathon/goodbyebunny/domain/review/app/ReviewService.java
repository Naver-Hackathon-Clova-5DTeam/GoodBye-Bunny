package com.clova.hackathon.goodbyebunny.domain.review.app;

import com.clova.hackathon.goodbyebunny.domain.keyword.dao.KeywordRepository;
import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import com.clova.hackathon.goodbyebunny.domain.member.dao.CustomMemberRepositoryImpl;
import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewCreateRequest;
import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewUpdateRequest;
import com.clova.hackathon.goodbyebunny.domain.review.api.response.ReviewReadResponse;
import com.clova.hackathon.goodbyebunny.domain.review.dao.ReviewKeywordRepository;
import com.clova.hackathon.goodbyebunny.domain.review.dao.ReviewRepository;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.domain.review.model.ReviewKeyword;
import com.clova.hackathon.goodbyebunny.global.util.ValueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final KeywordRepository keywordRepository;


    private final CustomMemberRepositoryImpl customMemberRepository;
    private final RestTemplate restTemplate;

    @Value("${secret.NCP_CLOVASTUDIO_API_KEY}")
    private String NCP_CLOVASTUDIO_API_KEY;

    @Value("${secret.NCP_APIGW_API_KEY}")
    private String NCP_APIGW_API_KEY;

    @Value("${secret.NCP_CLOVASTUDIO_REQUEST_ID}")
    private String NCP_CLOVASTUDIO_REQUEST_ID;


    // Review 작성
    @Transactional
    public ResponseEntity<Long> createReview(String memberNickname, ReviewCreateRequest request) {

        Member member = memberRepository.findMemberByNickname(memberNickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        reviewRepository.findReviewByMemberId(member.getId())
                .ifPresent((_review) -> {
                    throw new IllegalStateException("작성한 회고가 존재합니다.");
                });

        Review review = Review.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        Long savedCommentId = reviewRepository.save(review).getId();

        String clovaJson = sendRequest(request.getContent());
        //@TODO: json 파싱 제대로하기.
        String preFix = "data:{\"message\":{\"role\":\"assistant\",\"content\":\"";
        String suffix = "\"}";
        if (clovaJson.startsWith(preFix)) {
            String clovaResponse = clovaJson.substring(preFix.length(), clovaJson.indexOf(suffix));
            String[] relevances = clovaResponse.split("\\\\n");
            Set<Keyword> top3 = Arrays.stream(relevances)
                    .filter((r) -> r.endsWith(": 고"))
                    .map((r) -> r.substring(r.indexOf('-')+1,r.indexOf(':')))
                    .limit(3)
                    .map(keywordRepository::findKeywordByWord)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            if (top3.isEmpty()) {
                throw new IllegalStateException("매칭되는 키워드가 없습니다.");
            }
            for (Keyword keyword : top3) {

                ReviewKeyword reviewKeyword = ReviewKeyword.builder()
                        .keyword(keyword)
                        .review(review)
                        .build();
                reviewKeywordRepository.save(reviewKeyword);
            }


        }


        return ResponseEntity.ok(savedCommentId);

    }

    // 나의 Review 조회
    public ResponseEntity<ReviewReadResponse> getReview(String memberNickname) {


        //member 조회
        Member member = memberRepository.findMemberByNickname(memberNickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        //review 조회
        Review review = reviewRepository.findReviewByMemberId(member.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회고입니다."));

        //review 조회
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.findReviewKeywordsByReviewId(review.getId())
                .orElseThrow(() -> new IllegalStateException("키워드가 존재하지 않습니다."));
        List<String> keywordsList = new ArrayList<>();

        for (ReviewKeyword reviewKeyword : reviewKeywords) {
            String word = reviewKeyword.getKeyword().getWord();
            System.out.println(word); // 키워드 출력
            keywordsList.add(word); // 키워드 리스트에 추가
        }


        return ResponseEntity.ok(ReviewReadResponse.of(review.getId(), member.getNickname(), review.getTitle()
                , review.getContent(), review.getUpdatedDate(),keywordsList));

    }

    // 나의 Review 수정
    public ResponseEntity<Long> updateReview(String memberNickname, ReviewUpdateRequest request) {


        //member 조회
        Member member = memberRepository.findMemberByNickname(memberNickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        //review 조회
        Review review = reviewRepository.findReviewByMemberId(member.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회고입니다."));

        if (!member.getId().equals(review.getMember().getId())) {
            throw new IllegalStateException("허용되지 않은 접근입니다.");
        }

        review.updateReview(request.getContent());
        reviewRepository.save(review);


        return ResponseEntity.ok(review.getId());

    }

    public List<?> recommend(String nickName) {
        Member member = memberRepository.findMemberByNickname(nickName)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));

        return customMemberRepository.getMatchingMembers(member.getAge(), member.getId());
    }

    public String sendRequest(String content) {
        // 헤더 생성 및 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-CLOVASTUDIO-API-KEY",NCP_CLOVASTUDIO_API_KEY);
        headers.set("X-NCP-APIGW-API-KEY", NCP_APIGW_API_KEY);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", NCP_CLOVASTUDIO_REQUEST_ID);
        headers.set("Accept", "text/event-stream");


        // 요청 바디 설정 (예: JSON 문자열)
        String jsonBody = "{\n" +
                "  \"messages\" : [ {\n" +
                "    \"role\" : \"system\",\n" +
                "    \"content\" : \"키워드: 1-가족, 2-친구, 3-연인, 4-학업, 5-업무, 6-직장, 7-건강, 8-여행, 9-취미\\n\\n지시: 위 9개 키워드의 순서대로 사용자 입력과의 연관성을 고/중/저 중 하나로 평가한다. 각 평가는 별도의 줄에 1번부터 9번까지 차례대로 출력한다.\\n9개 키워드에 대한 평가를 모두 출력한다.\\n\\n출력 형식: \\n1-가족: [연관도]\\n2-친구: [연관도]\\n3-연인: [연관도]\\n4-학업: [연관도]\\n5-업무: [연관도]\\n6-직장: [연관도]\\n7-건강: [연관도]\\n8-여행: [연관도]\\n9-취미: [연관도]\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"" + content + "\\n\\n지시: \\n키워드: 1-가족, 2-친구, 3-연인, 4-학업, 5-업무, 6-직장, 7-건강, 8-여행, 9-취미\\n\\n\\n지시: 위 9개 키워드의 순서대로 사용자 입력과의 연관성을 고/중/저 중 하나로 평가한다. 각 평가는 별도의 줄에 1번부터 9번까지 차례대로 출력한다.\\n9개 키워드에 대한 평가를 모두 출력한다.\\n\\n\\n출력 형식: \\n1-가족: [연관도]\\n2-친구: [연관도]\\n3-연인: [연관도]\\n4-학업: [연관도]\\n5-업무: [연관도]\\n6-직장: [연관도]\\n7-건강: [연관도]\\n8-여행: [연관도]\\n9-취미: [연관도]\"\n" +
                "  } ],\n" +
                "  \"topP\" : 0.8,\n" +
                "  \"topK\" : 0,\n" +
                "  \"maxTokens\" : 2513,\n" +
                "  \"temperature\" : 0.26,\n" +
                "  \"repeatPenalty\" : 5.0,\n" +
                "  \"stopBefore\" : [ ],\n" +
                "  \"includeAiFilters\" : true\n" +
                "}";

        // HttpEntity 생성 (헤더와 바디 결합)
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 요청 보내기 (예: POST 요청)
        String url = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-002";
        String response = restTemplate.postForObject(url, entity, String.class);
        String result = null;
        Iterator<String> iterator = ValueUtil.firstNonNull(response, "").lines().iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.startsWith("event:result") && iterator.hasNext()) {
                result = iterator.next();
                break;
            }
        }

        // 응답 처리
        return result;
    }

}
