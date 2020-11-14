package com.example.demo.sample.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ResponseMessageVO extends BasicResponseVO{

	@JsonProperty("list") // json 리턴시 필드 이름을 변경해서 리턴한다.
	private Map<String, Object> result;

	public ResponseMessageVO() {
		this(HttpStatus.OK);
	}
	
	public ResponseMessageVO(HttpStatus httpStatus) {
		super(httpStatus);
		this.result = new HashMap<>();
	}
	
	// Basic 메시지 말뚝에다가 Map에 키값을 가변적으로 넣다 뺄다 할 수 있도록 
	public void add(String key, Object result) {
		this.result.put(key, result);
	}

	public void remove(String key) {
		this.result.remove(key);
	}
	
	
	// data를 포함한 응답코드 리턴
//	public ResponseMessageVO getMessageAll() {
//		return this;
//	}
}
