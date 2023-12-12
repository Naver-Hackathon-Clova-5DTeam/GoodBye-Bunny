package com.clova.hackathon.goodbyebunny.global.security.provider;


import com.clova.hackathon.goodbyebunny.global.security.MemberDetailService;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final MemberDetailService memberDetailService;

    public long ACCESS_TOKEN_VALID_TIME = 24 * 60 * 60 * 1000L;

    private String secretKey = "GOODBYE_BUNNY_SECRET_JWT_KEY_KEY_KEY";

    @PostConstruct
    private void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(final String nickname) {
        Claims claims = Jwts.claims().setSubject(nickname);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    public Claims getAllClaims(final String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(final String token){
        Date expiration = this.getAllClaims(token).getExpiration(); //문제지점
        return expiration.after(new Date());
    }

    public boolean validateToken(final String token){
        try{
            return this.isExpired(token);
        }catch(Exception e){
            return false;
        }

    }

    public Authentication getAuthentication(final String token){
        Claims claims = this.getAllClaims(token);
        MemberDetails memberDetails = memberDetailService.loadMemberByEmail(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }
}
