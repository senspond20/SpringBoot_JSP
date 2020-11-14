package com.example.demo.sample.model;

import java.util.Date;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BasicResponseVO {

	private int code;
//	private boolean status;
	private String message;
	private Date timestamp;
	
	public BasicResponseVO() {
		this(HttpStatus.OK);
	}
	
	public BasicResponseVO(HttpStatus httpStatus) {
        this.code = httpStatus.value();
//      this.status = (httpStatus.isError())? false:true;
        this.message = httpStatus.getReasonPhrase();
        this.timestamp = new Date();
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.code = httpStatus.value();
//      this.status = (httpStatus.isError())? false:true;
        this.message = httpStatus.getReasonPhrase();
//        this.timestamp = new Date();
	}
	
	
}
