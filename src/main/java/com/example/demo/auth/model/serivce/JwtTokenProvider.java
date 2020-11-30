package com.example.demo.auth.model.serivce;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
    private String SECRETKEY;
    private long validityInMilliseconds;

    public JwtTokenProvider() {
        this.SECRETKEY = "huniverse"; // 복호화에 필요한 시크릿 키
        this.validityInMilliseconds = System.currentTimeMillis();
    }

    
    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);

        Date now = new Date();
        Date validity = new Date(now.getTime()
                + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRETKEY)
                .compact();
    }
    
    
    
    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETKEY))
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public Boolean validateToken(String token) {
    	// 토큰 검증해서 유효한 토큰값이면 true 리턴
    	// 아니면 false 리턴
    	
//    	var decoded_data = JWT.verify(token, SECRETKEY);
    	
    	return true;
    }

}
