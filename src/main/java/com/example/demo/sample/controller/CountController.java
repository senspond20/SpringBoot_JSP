package com.example.demo.sample.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rest.domain.ResponseMessage;
import com.example.demo.sample.model.BasicResponseVO;
import com.example.demo.sample.model.ResponseMessageVO;

// 1부터 num까지 더하는 api 이다.

@RestController
@RequestMapping("/count")
public class CountController {
	
	    // get은 GetMapping으로 간단하게 쓸 수 있다. 스프링프레임워크 낮은버전에는 없다.
	
		// /count/method1?num=100
	    @GetMapping("/sum1")
//		@ResponseBody
		public String method1(@RequestParam("num") int num) {
			int sum = 0;
			for(int i = 1; i <= num; i ++) {
				sum += i;
			}
			return Integer.toString(sum);
		}
		
		// 외부에 공개되는 api는 url패턴을 보고 어떤 Http 메소드인지 노출시키는 api는 설계하지 않는것이 좋을 수도 있다.
		// get방식일 경우 ?num=100 과 같은 쿼리스트링을 보고 get방식인것을 유추할 수 있기때문이다.
		
		// /count/method2/100
		@GetMapping("/sum2/{num}")
//		@ResponseBody
		public int method2(@PathVariable("num") int num) {
			int sum = 0;
			for(int i = 1; i <= num; i ++) {
				sum += i;
			}
			return sum; //Integer.toString(sum);
		}
		
	
		// /count/count3/문자열 --> 숫자대신 문자열을 넣었을떄 예외처리를 해주면서 일련의 패턴으로 알려준다.
		// 아래 url를 조회해보면 트위터 API의 경우 어떤식으로 응답오류 메시지를 주는지 볼 수 있다.
		// https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=username
		// -> 트위터 : {"errors":[{"code":215,"message":"Bad Authentication data."}]}
		// 
		@GetMapping("/sum3/{num}")
//		@ResponseBody
		public ResponseEntity<Object> method3(@PathVariable("num") String num) {
			int n = 0;
			
			try {
		     	n = Integer.parseInt(num);
		    } catch(NumberFormatException e) {  //문자열이 나타내는 숫자와 일치하지 않는 타입의 숫자로 변환 시 발생
		    	Map<String,Object> map = new HashMap<String, Object>();
		    	map.put("code", HttpStatus.BAD_REQUEST.value());
		    	map.put("message", e.getMessage());
		    	map.put("timestamp", new Date().toString());
		    //	{"code":400,"message":"For input string: \"100df\"","timestamp":"Sat Nov 14 22:20:00 KST 2020"}
		    	return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		    }
			int sum = 0;
			for(int i = 1; i <= n; i ++) {
				sum += i;
			}
			return new ResponseEntity<>(sum, HttpStatus.OK);
		}
		
//		num 이 null인 경우를 받을 수 있게 하고 처리
		
		@GetMapping({"/sum4/{num}","/sum4"})
//		@ResponseBody
		public ResponseEntity<Object> method4(@Nullable @PathVariable("num") String num) {
			int n = 0;
			Map<String,Object> map = new HashMap<String, Object>();
			if(num == null) {
				map.put("code", HttpStatus.BAD_REQUEST.value());
		    	map.put("message", "입력하라고!");
		    	map.put("timestamp", new Date().toString());
		    	return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
			}

			try {
		     	n = Integer.parseInt(num);
		    } catch(NumberFormatException e) {  //문자열이 나타내는 숫자와 일치하지 않는 타입의 숫자로 변환 시 발생
		    	map.put("code", HttpStatus.BAD_REQUEST.value());
		    	map.put("message", e.getMessage());
		    	map.put("timestamp", new Date().toString());
		    	// {"error":"umberFormatException","message":"For input string: \"gdg\"","timestamp":"Sat Nov 14 21:11:40 KST 2020"}
		    	return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		    } 
		
			int sum = 0;
			for(int i = 1; i <= n; i ++) {
				sum += i;
			}
			
			map.put("code", HttpStatus.OK.value());
	    	map.put("message", "success");
	    	map.put("timestamp", new Date().toString());
	    	map.put("data", sum);
	    	return new ResponseEntity<>(map, HttpStatus.OK);
	    	
		}
		
		// 모든 API 마다 일일히 이러한 예외처리를 하는것은 많은 노가다를 유발시키는데
		// 이부분을 공통코드로는 어떻게 ?
		// => 스프링프레임워크 5버전 이상이나, 스프링부트에서는 @ControllerAdvice 어노테이션으로 쉽게 구현이 가능
		// 낮은 버전의 스프링프레임워크에서는 어떻게 해야하나?
		
		// 일단 응답메시지 클래스를 만들어보자.
		@GetMapping("/sumtest")
//		@ResponseBody
		public ResponseEntity<ResponseMessageVO> method5() {
			int n = 100;
			
			ResponseMessageVO message = new ResponseMessageVO();
			int sum = 0;
			for(int i = 1; i <= n; i ++) {
				sum += i;
			}
			HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
			
			message.setHttpStatus(status);
			message.add("desc", "1부터" + n +"까지의 합");
			message.add("data", sum);
//			return b;
	    	return new ResponseEntity<>(message, status);
	    	
		}
		
}





/*
REST API 란 :
	 REST : Representational State Transfer 의 약자
		 자원을 이름(자원의 표현)으로 구분하여 해당 자원의 상태(정보)를 주고 받는 WEB API를 의미한다.
	
		
	HTTP Method : GET/POST/PUT/DELETE
	CRUD : Read / Create / update / delete
	 SQL : select / insert/ update/ delete
	
	
	 HTTP Method 형식에 맞게 CRUD ,SQL 스펙이 일치하는것이 원칙.
	 클라이언트(API 사용자)가 API만 보고도 어떠한 API인지 알아 볼 수 있도록 설계해야한다.
	 그리고 어떠한 요청을 보냈을때 서버가 어떠한 상태인지 알 수 있도록 일련의 형식을 만들어서 보내줘야 한다.
	 ** REST API 설계 원칙 참고..
	
	 그런데 REST API에는 확실한 표준이 없다. 기업들마다 서로 제각각 일수가 있다..
	
	 백엔드와 프론트엔드가 분리되가는 현 생태계에서 더 큰 필요성이 부각.(프론트 엔드 프레임워크의 발전으로 인한 영향도 큼)
	( Rest방식을 사용하게 되면 백엔드를 자바 스프링으로 구성하고 프론트는 전혀 다른 플랫폼인 앵귤러,리액트 등으로도 구성하는것도 가능해진다.)
		
*/
	 
