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
	// /sample/count1?num=100
	@GetMapping("/count1")
	@ResponseBody
	public String count1(@RequestParam("num") int num) {
		int sum = 0;
		for(int i =0; i < num; i ++) {
			sum += i;
		}
		return Integer.toString(sum);
	}
	// /sample/count2/100
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
	@GetMapping("/hello4")
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
	
	@GetMapping("/test1")
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
	
}