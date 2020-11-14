package com.example.demo.sample;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

// @RestController 
// @RestController = @Controller + @ResponseBody 개념으로 
/*
   @RestController라는 어노테이션이 컨트롤러에 붙으면 이 클래스에 속한 모든 메소드는
   @ResponseBody가 붙어있다고 생각하면 된다.
*/
@Controller
@RequestMapping("/sample")
public class SampleController {
	
	// ResponseBody 있고 없고 차이 확인
	
	// 1. String 으로 리턴
	
	@RequestMapping(value = "/hello1", method = RequestMethod.GET)
	public String hello1() {
		return "hello"; // hello는 View 이름을 의미 -> 설정한 suffix가 붙어서 hello.jsp를 반환
	}
	
	@RequestMapping(value = "/hello2", method = RequestMethod.GET)
	@ResponseBody
	public String hello2() {
		return "hello";  // hello는 문자열 그자체를 의미.
	}
	
	// get은 GetMapping으로 간단하게 쓸 수 있다.
	// 1부터 num까지 더하는 api 
	// /home/count1?num=100
	@GetMapping("/count1")
	@ResponseBody
	public String count1(@RequestParam("num") int num) {
		int sum = 0;
		for(int i =0; i < num; i ++) {
			sum += i;
		}
		return Integer.toString(sum);
	}
	// /home/count2/100
	@GetMapping("/count2/{num}")
	@ResponseBody
	public String count2(@PathVariable("num") int num) {
		int sum = 0;
		for(int i =0; i < num; i ++) {
			sum += i;
		}
		return Integer.toString(sum);
	}
	
	// ModelAndView
	@GetMapping("/hello3") 
	public ModelAndView hello3() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("data","success");
		mv.setViewName("hello");
		return mv; // hello.jsp에 모델을 담아서 반환
	}
	
	// jackson jsonView를 사용하는 사용하는 방법 
	// -> 기존 MVC 패턴으로 만든 컨트롤러단의 메소드를 큰 수정 없이 view 이름만 jsonView로 바꿔줘서 WEB API로 만들 수 있다는 장점이 있다.
	// @Configuration 을 붙인 WebConfig 클래스에서
	// 다음과 같이 jsonView 빈등록하고 
	/*
	@Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }
    */
	
	/* 스프링 프레임워크에서 xml방식으로 많이 되어있고 DispathcerServlet 을 설정하는 xml에서 
	<bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJacksonJsonView">
    	<property name="contentType" value="application/json;charset=UTF-8"> </property>
	</bean>
 
	 */
	// WebConfig 주석처리.
	
	// ModelAndView
	@RequestMapping(value = "/hello4", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView hello4() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("data","success");
		mv.setViewName("jsonView");
		return mv; // hello.jsp에 모델을 담아서 반환
	}
	
	
	
	// 스프링 부트에서는 스프링 프레임워크처럼 수동으로 라이브러리를 넣지 않아도
	// jackson data-bind 가 포함되어 있어서 자동으로 json으로 바인딩해주는 결과를 볼 수 있다.
	// 또한 한글 utf-8 인코딩 설정을 하지 않아도 한글이 깨지지 않는것을 확인할 수 있다.
	
	//@GetMapping("/rest/test1")
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> test1() {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("이름", "장나라");
		map.put("나이", "27");
		map.put("사는곳","경기도");
		return map;
	}
	
	
	// ResponseBody객체로 리턴을 할 수 있는것에는 
	// 404 응답코드를 던지는것을 확인
	
	@GetMapping("/test2")
	//ResponseEntity<T> 타입으로 리턴할 때는 @ResponseBody 를 붙이지 않아도 에러는 없다.
	public ResponseEntity<Map<String,Object>> test2() {
		
		// DB에서 select해온 데이터로 가정
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("이름", "장나라");
		map.put("나이", "27");
		map.put("사는곳","경기도");
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
	
	// REST API 란 :
	// REST : Representational State Transfer 의 약자
		// 자원을 이름(자원의 표현)으로 구분하여 해당 자원의 상태(정보)를 주고 받는 WEB API를 의미한다.
	
		
	// HTTP Method : GET/POST/PUT/DELETE
	// CRUD : Read / Create / update / delete
	// SQL : select / insert/ update/ delete
	
	/*
	 */
	// HTTP Method 형식에 맞게 CRUD ,SQL 스펙이 일치하는것이 원칙.
	// 클라이언트(API 사용자)가 API만 보고도 어떠한 API인지 알아 볼 수 있도록 설계해야한다.
	// 그리고 응답코드를 보내주어서 데이터를 잘 수신했는지 확인하게 할 수 있도록 해줘야 하는등..	
	// **REST API 설계 원칙 참고..
	
	// < 그런데 REST API에는 확실한 표준이 없다. 기업들마다 서로 제각각 일수가 있다..>
	
	// 백엔드와 프론트엔드가 분리되가는 현 생태계에서 더 큰 필요성이 부각.(프론트 엔드 프레임워크의 발전으로 인한 영향도 큼)
	// ( Rest방식을 사용하게 되면 백엔드를 자바 스프링으로 구성하고 프론트는 전혀 다른 플랫폼인 앵귤러,리액트 등으로도 구성하는것도 가능해진다.)
		

	// MVC -> REST -> GraphQL(최신기술)
	
	// REST 방식은 사전에 어떤식으로 API를 주고받을지에 대한 사전정의를 하고 그 정의대로 데이터를 주고 받지만,,
	// 이러한 자원중심의 방식은 구현이 간단하고 정교하게 만들 수 있지만 유연성이 떨어지는 단점이 있다.
	// 그리고 각 자원마다 각각의 엔드포인트(url)가 필요해지고 
	// 큰 규모일수록 많은 엔드포인트로 관리해야 할 엔드포인트가 늘어나 피곤해진다.
	// 그런데 클라이언트의 요구가 수시로 변한다면..? 갑자기 다른 형식으로 보내달라고 하면? 
	
	// 이러한 단점을 보완해 페이스북에서 개발한 GraphQL이라는 API용 쿼리언어가 등장.
	
	// 단 한개의 엔드포인트(url) API에 
	// 사용자가 던지는 형식대로 자유자재로 API에서 데이터를 원하는 형식대로 가져올 수 있게 만든다.
	// 수시로 변하는 클라이언트의 요구를 GraphQL로 완벽하게 대응해 줄 수 있도록 만들었다.
	// 즉.. 백엔드 개발자가 한번 잘 만들어놓고 사용방법만 클라이언트 쪽에 잘 알려주면 그담부터 별로 할게 없다!

	// https://graphql-kr.github.io/
	// https://brunch.co.kr/@springboot/191
	// https://engineering.huiseoul.com/백엔드-개발자가-빨리-퇴근하는법-23168f6a4080


	/* springboot 에서 graphql 사용하기
	  
	   <dependency>
		    <groupId>com.graphql-java</groupId>
		    <artifactId>graphql-spring-boot-starter</artifactId>
		    <version>5.0.2</version>
		</dependency>
		<dependency>
		    <groupId>com.graphql-java</groupId>
		    <artifactId>graphql-java-tools</artifactId>
		    <version>5.2.4</version>
		</dependency>

	 */
}
