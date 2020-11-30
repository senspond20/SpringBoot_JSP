package com.example.demo.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {


	@GetMapping("/board/")
	public ResponseEntity<?> getPostList(){
		return ResponseEntity.ok().build();
	}
	
	
}
