package com.example.demo.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.model.serivce.JwtService;
import com.example.demo.auth.model.serivce.JwtTokenProvider;
import com.example.demo.auth.model.vo.Member;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	/*
	 
	HTTP/1.1 200 OK
	Content-Type: application/json
	Cache-Control: no-store
	Pragma: no-cache
	 
	{
	  "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
	  "token_type":"bearer",
	  "expires_in":3600,
	  "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
	  "scope":"create"
	}

	 */
	@SuppressWarnings("unused")
	@PostMapping("/signin")
	public ResponseEntity<?> signin(String email, String password, HttpServletResponse res) {
	
		// service -> dao 를 통해 db서 비밀번호 일치하는 회원 확인
		// 그런 회원이 없으면 권한 없다고 출력
		Member member = new Member(email,password);
		if(false) {
			return ResponseEntity.badRequest().body("로그인 실패. 비밀번호를 확인하세요.");
		}else {
			// 비번 일치하면 토큰값 생성
			String token = jwtTokenProvider.createToken(member.getEmail());
			
//			String token = jwtService.createMember(member);
			res.setHeader("Authorization","bearer " + token);
//			result.setData(m);
			
			Map<String,Object> responseToken = new HashMap<>();
			responseToken.put("access_token", token);
			responseToken.put("tokenType", "bearer");
			    	
			return ResponseEntity.ok().body(responseToken);
		}
	}
	
	@PostMapping("/testtest")
	public String testtset(HttpServletRequest request) {
		String token = request.getParameter("Authorization");
		System.out.println(token);
		return jwtTokenProvider.getSubject(token);
	}
	@PostMapping("/test")
	public ResponseEntity<?> test(HttpServletRequest request) {
		String token = request.getHeader("Authorization");	
		if(jwtTokenProvider.validateToken(token)) {
			return ResponseEntity.badRequest().body("인증 거부! 권한이 없습니다.");
		}else {
			return ResponseEntity.ok().body("인증 성공!");
		}
	}
//	
//	 public String createToken(LoginRequest loginRequest) {
//	        Member member = memberRepository.findByEmail(loginRequest.getEmail())  // email로 등록된 회원을 찾는다.
//	                .orElseThrow(NotExistEmailException::new);
//
//	        if (!member.checkPassword(loginRequest.getPassword())) {  // 유저가 보유한 패스워드와 입력받은 패스워드가 일치하는 지 확인한다.
//	            throw new WrongPasswordException();
//	        }
//
//	        return jwtTokenProvider.createToken(loginRequest.getEmail()); // email 정보만 가지고 token을 만든다.
//	    }
	 
}
