package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserParam;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired 
	// 자동 선연결 (BEAN이 등록된 애들(Spring Container가 관리하는 객체)중을 연결해준다(단 1개만연결)
	private UserService service;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		System.out.println("Controller - Login ");
		
		model.addObject(Const.TITLE,"Login");
		model.addObject(Const.VIEW,"/user/login");
		model.setViewName(ViewRef.TEMP_DEFAULT);
		//return을 void로하면 css가 적용이안되지만 
		//리턴타입을 ModelAndView로하고 리턴을 parameter로 하게된다면 css적용되있는 것이 유지가 된다.
		
		return model;
//		model.addAttribute(Const.TITLE,"Login");
//		model.addAttribute(Const.VIEW,"/user/login");
//		return ViewRef.TEMP_DEFAULT;
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String loginProc(UserDTO param, Model model /* HttpSession hs, RedirectAttributes ra*/) {
//		System.out.println("id: " + param.getUser_id());
//		System.out.println("pw: " + param.getUser_pw());
//		int result = service.login(param);
//		System.out.println("login result:" + result);
//		if(result==1) {
//			return "redirect:/restaurant/map";
//		}
//		else if (result ==2) {model.addAttribute("msg", "id check");}
//		else {model.addAttribute("msg", "pw check");}
//		model.addAttribute(Const.VIEW,"/user/login");
//		return ViewRef.TEMP_DEFAULT;
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginProc(UserParam param, HttpSession hs, RedirectAttributes ra) {
		System.out.println("id: " + param.getUser_id());
		System.out.println("pw: " + param.getUser_pw());
		int result = service.login(param);
		System.out.println("login result:" + result);
		if(result==Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/restaurant/map";
		}
		String msg =  null;
		if(result == Const.NO_ID) {
			msg="Id Check!!";
		} else if(result == Const.NO_PW) {
			msg="Pw Check!!";
		}
		param.setMsg(msg);
		// session에 박히고 세션이 끝나면 지워진다
		ra.addFlashAttribute("data", param);
		return "redirect:/user/login";
	}
	
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	//변수와 보내는 속성이름이 같지않다면 따로 설정을해줘야된다
	public String join(Model model,  @RequestParam(defaultValue="0") int err) {
		System.out.println("Controller - Jo ");
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
	public String join(UserVO param, RedirectAttributes ra) {
		int result = service.join(param);
		System.out.println("join result: " + result);
		if(result==1) {
			return "redirect:/user/login";
		}
		ra.addFlashAttribute("err", result);
		
		return "redirect:/user/join?err=" + result;
	}
	
	@RequestMapping(value="/ajaxIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxIdChk(@RequestBody UserParam param) {
		System.out.println("user_id : " + param.getUser_id());
		int result = service.login(param);
		return String.valueOf(result);
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect:/user/login";
	}
	
}
