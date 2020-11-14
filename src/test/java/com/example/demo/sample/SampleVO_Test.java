package com.example.demo.sample;

import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

// lombok Test With Junit4
@Slf4j
public class SampleVO_Test {

	private SampleVO sv;
	
	@Before
	public void setup() {
		sv = new SampleVO("admin","1234");
	}
	@Test
	public void test() {
		log.debug(sv.getId());
		log.debug(sv.getName());
		log.debug(sv.toString());
		sv.setName("nayoung");
		log.debug(sv.toString());
	}

}
