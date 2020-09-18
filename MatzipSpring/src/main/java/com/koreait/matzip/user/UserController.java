package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired 
	// 자동 선연결 (BEAN이 등록된 애들(Spring Container가 관리하는 객체)중을 연결해준다(단 1개만연결)
	private UserService service;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(Const.TITLE,"Login");
		model.addAttribute(Const.VIEW,"/user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginProc(UserDTO param) {
		System.out.println("id: " + param.getUser_id());
		System.out.println("pw: " + param.getUser_pw());
		int result = service.login(param);
		System.out.println("login result:" + result);
		if(result==1) {
			return "redirect:/restaurant/map";
		}
		
		return "redirect:/user/login";
	}
	
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	//변수와 보내는 속성이름이 같지않다면 따로 설정을해줘야된다
	public String join(Model model,  @RequestParam(defaultValue="0") int err) {
		System.out.println("err:" + err);
		
		if(err>0) {
			model.addAttribute("msg","Error occured!!!!");
		}
		model.addAttribute(Const.TITLE,"Join");
		model.addAttribute(Const.VIEW,"/user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	
	
	// Proc에 하던 기능 (무조건 POST형식으로 보내야한다)
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVO param) {
		int result = service.join(param);
		System.out.println("join result: " + result);
		if(result==1) {
			return "redirect:/user/login";
		}
		return "redirect:/user/join?err=" + result;
	}
}
