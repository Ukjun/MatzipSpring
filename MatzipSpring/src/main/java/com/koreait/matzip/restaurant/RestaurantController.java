package com.koreait.matzip.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.restaurant.model.RestaurantParam;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	@RequestMapping("/map")
	public String map(Model model) {
		model.addAttribute(Const.TITLE,"Map");
		model.addAttribute(Const.VIEW, "restaurant/map");
		return ViewRef.TEMP_MAP;
	}
	
	@RequestMapping("/ajaxGetList")
	@ResponseBody
	public String ajaxGetList(RestaurantParam param) {
		System.out.println("sw_lat: " +param.getSw_lat());
		System.out.println("sw_lng: " +param.getSw_lng());
		System.out.println("ne_lat: " +param.getNe_lat());
		System.out.println("ne_lng: " +param.getNe_lng());
		return "";
	}
}
