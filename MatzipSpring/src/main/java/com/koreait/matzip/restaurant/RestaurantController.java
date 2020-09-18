package com.koreait.matzip.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String map() {
		return "restaurant/map";
	}
}
