package com.ctn.springpractice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

	@GetMapping("/test")
	public ResponseEntity<String> testMethod(){
		return ResponseEntity.ok("I am Called");
	}
}
