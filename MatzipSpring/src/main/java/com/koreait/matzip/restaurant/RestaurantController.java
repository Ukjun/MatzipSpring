package com.koreait.matzip.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	@RequestMapping("/map")
	public String map(Model model) {
		model.addAttribute(Const.TITLE,"Map");
		model.addAttribute(Const.VIEW, "restaurant/map");
		return ViewRef.TEMP_MAP;
	}
}
