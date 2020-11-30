package com.example.demo.auth.model.serivce;

import com.example.demo.auth.model.vo.Member;

public interface JwtService {

	<T> String create(String key, T data, String subject);

	String createMember(Member member);

	
}
