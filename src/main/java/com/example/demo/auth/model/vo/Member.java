package com.example.demo.auth.model.vo;

import lombok.Data;

@Data
public class Member {

	private String id;
	private String password;
	private String email;
	public Member() {
	}
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
