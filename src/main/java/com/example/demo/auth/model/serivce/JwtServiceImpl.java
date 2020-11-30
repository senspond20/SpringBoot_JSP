package com.example.demo.auth.model.serivce;

import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Service;

import com.example.demo.auth.model.vo.Member;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/*
 

<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.7.0</version>
</dependency>


 */
@Slf4j
@Service("jwtService")
public class JwtServiceImpl implements JwtService{

	private static final String SALT =  "gunSecret";
	
	// JSON Web Token (JWT)은 JSON 객체로서 당사자 간에 안전하게 정보를 전송할 수 있는 작고 독립적인 방법을 정의하는 공개 표준 (RFC 7519)입니다. 
	// 이 정보는 디지털로 서명 되었기 때문에 신뢰할 수 있습니다. JWT는 암호 (HMAC 알고리즘 사용) 또는 RSA를 사용하는 공용 / 개인 키 쌍을 사용하여 서명을 할 수 있습니다
	
	// jjwt를 이용하여 JWT를 만드는 코드입니다. JWT의 헤더, 클래임, 암호 등의 필요한 정보를 넣고 직렬화(compact())시켜줍니다. 
	
	@Override
	public <T> String create(String key, T data, String subject) {
		String jwt = Jwts.builder()
								.setHeaderParam("typ", "JWT")
								.setHeaderParam("regDate",System.currentTimeMillis())
								.setSubject(subject)
								.claim(key, data)
								.signWith(SignatureAlgorithm.HS256, this.generateKey())
								.compact();
		return jwt;
	}

	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}

	@Override
	public String createMember(Member member) {		
		String jwt = Jwts.builder()
		.setHeaderParam("typ", "JWT")
		.setHeaderParam("regDate",System.currentTimeMillis())
		 .setSubject("users/TzMUocMF4p")
		 .claim("name", "Robert Token Man")
		  .claim("scope", "self groups/admins")
		.signWith(SignatureAlgorithm.HS256, this.generateKey())
		.compact();// TODO Auto-generated method stub
		 
		return jwt;
	}



}
