package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PetController {
	
	@RequestMapping("/pcs")
	@ResponseBody // response'un body'si olduğunu söylemek için
	public String welcome() {
		return "welcome to petclinic";
	}
	
	
}
