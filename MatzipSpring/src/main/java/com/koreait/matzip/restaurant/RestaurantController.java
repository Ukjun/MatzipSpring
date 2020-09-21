package com.koreait.matzip.restaurant;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.restaurant.model.RestaurantParam;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	private RestaurantService service;
	
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
	
	
	@RequestMapping("/restReg")
	public String mapper(Model model) {
		model.addAttribute(Const.TITLE,"등록");
		model.addAttribute(Const.VIEW,"restaurant/restReg");
		
		return ViewRef.TEMP_MAP;
	}
	
	@RequestMapping(value= "/restregProc", method= RequestMethod.POST)
	public String insRestaurant(RestaurantParam param, HttpSession hs, RedirectAttributes ra) {
		UserVO vo = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		int i_user = vo.getI_user();
		param.setI_user(i_user);
		System.out.println("i_user: " + i_user);
		int result = service.insRest(param);
		
		return "redirect:/restaurant/map";
	}
}
