package com.example.demo.sample.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class ApiController {

	@RequestMapping(value="/getNow", method=RequestMethod.GET)
	public ResponseEntity<?> getNow(){ 
          String str = "스프링부트에서 리액트로 보내기"; 
		  return ResponseEntity.ok().body(str);
	   } 
}
