# SpringBoot_JSP

+ Spring Boot 2.4.0
+ OpenJdk 11
+ maven 방식

## INIT 
+ spring-boot-starter

+ 추가한 dependency
    + spring-web
    + spring-api
    + spring-boot-devtools
    + lombok
    + jsp 관련

```xml

<!-- jsp -->
<dependency> 
	<groupId>javax.servlet</groupId> 
	<artifactId>jstl</artifactId> 
</dependency> 

<dependency> 
	<groupId>org.apache.tomcat.embed</groupId> 
	<artifactId>tomcat-embed-jasper</artifactId> 
</dependency> 
```

+ application.properties
```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

+ kill 8080 .bat
```

@ECHO OFF
ECHO ---------------------------------------------------------
ECHO ------[ kill 8080 port !!! ]-------
ECHO ---------------------------------------------------------
SET killport=8080
for /f "tokens=5" %%p in ('netstat -aon ^| find /i "listening" ^| find "%killport%"') do taskkill /F /PID %%p
pause
```

## Sample 

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring Boot Application with JSP</title>
</head>
<body>
	Hello, Spring Boot App 
	
	<c:if test="${not empty data}">
		<div> data : <span>${data }</span></div>
	</c:if>
</body>
</html>

```

```java
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
	d
}
```

```java

// 1부터 num까지 더하는 api 이다.

@RestController
@RequestMapping("/count")
public class CountController {
	
	    // get은 GetMapping으로 간단하게 쓸 수 있다. 스프링프레임워크 낮은버전에는 없다.
	
		// /count/method1?num=100
	    @GetMapping("/method1")
//		@ResponseBody
		public String method1(@RequestParam("num") int num) {
			int sum = 0;
			for(int i =0; i < num; i ++) {
				sum += i;
			}
			return Integer.toString(sum);
		}
		
		// 외부에 공개되는 api는 url패턴을 보고 어떤 Http 메소드인지 노출시키는 api는 설계하지 않는것이 좋다.
		// get방식일 경우 ?num=100 과 같은 쿼리스트링을 보고 get방식인것을 유추할 수 있기때문이다.
		
		// /count/method2/100
		@GetMapping("/method2/{num}")
//		@ResponseBody
		public int method2(@PathVariable("num") int num) {
			int sum = 0;
			for(int i =0; i < num; i ++) {
				sum += i;
			}
			return sum; //Integer.toString(sum);
		}
		
	
		// /count/count3/문자열 --> 숫자대신 문자열을 넣었을떄 예외처리를 해주면서 일련의 패턴으로 알려준다.
		// 아래 url를 조회해보면 트위터 API의 경우 어떤식으로 응답오류 메시지를 주는지 볼 수 있다.
		// https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=username
		// -> 트위터 : {"errors":[{"code":215,"message":"Bad Authentication data."}]}
		// 
		@GetMapping("/method3/{num}")
//		@ResponseBody
		public ResponseEntity<Object> method3(@PathVariable("num") String num) {
			int n = 0;
			try {
		     	n = Integer.parseInt(num);
		    } catch(NumberFormatException e) {  //문자열이 나타내는 숫자와 일치하지 않는 타입의 숫자로 변환 시 발생
		    	Map<String,String> map = new HashMap<String, String>();
		    	map.put("error", "umberFormatException");
		    	map.put("message", e.getMessage());
		    	map.put("timestamp", new Date().toString());
		    	// {"error":"umberFormatException","message":"For input string: \"gdg\"","timestamp":"Sat Nov 14 21:11:40 KST 2020"}
		    	return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		    }
			
			int sum = 0;
			for(int i =0; i < n; i ++) {
				sum += i;
			}
			return new ResponseEntity<>(sum, HttpStatus.OK);
		}
		
		// 모든 API 마다 일일히 이러한 예외처리를 하는것은 많은 노가다를 유발시키는데
		// 이부분을 공통코드로는 어떻게 ?
		// => 스프링프레임워크 5버전 이상이나, 스프링부트에서는 @ControllerAdvice 어노테이션으로 쉽게 구현이 가능
		// 낮은 버전의 스프링프레임워크에서는 어떻게 해야하나?
		
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
	 

```
