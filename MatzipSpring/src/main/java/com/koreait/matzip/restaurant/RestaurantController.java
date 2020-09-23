package com.koreait.matzip.restaurant;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.restaurant.model.RestaurantDMI;
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
	
	//한글 깨짐 방지
	@RequestMapping(value= "/ajaxGetList" , produces ="application/json; charset=UTF-8")
	@ResponseBody
	public List<RestaurantDMI> ajaxGetList(RestaurantParam param) {
		System.out.println("sw_lat: " +param.getSw_lat());
		System.out.println("sw_lng: " +param.getSw_lng());
		System.out.println("ne_lat: " +param.getNe_lat());
		System.out.println("ne_lng: " +param.getNe_lng());
		return service.selRestList(param);
	}
	
	
	@RequestMapping("/restReg")
	public String restReg(Model model) {
		model.addAttribute("categoryList", service.selCategoryList());
		model.addAttribute(Const.TITLE,"등록");
		model.addAttribute(Const.VIEW,"restaurant/restReg");
		
		return ViewRef.TEMP_MAP;
	}
	
	@RequestMapping(value= "/restregProc", method= RequestMethod.POST)
	public String insRestaurant(RestaurantParam param, HttpSession hs, RedirectAttributes ra) {
//		UserVO vo = (UserVO)hs.getAttribute(Const.LOGIN_USER);
//		int i_user = vo.getI_user();
//		param.setI_user(i_user);
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		System.out.println("i_user: " + i_user);
		service.insRest(param);
		
		return "redirect:/restaurant/map";
	}
	
	@RequestMapping("/restDetail")
	public String detailRestaurant(Model model, RestaurantParam param) {
		RestaurantDMI vo = service.detailRest(param);
		service.addHits(param);
		model.addAttribute("data", vo);
		model.addAttribute(Const.TITLE,"등록");
		model.addAttribute(Const.VIEW,"restaurant/restDetail");
		
		System.out.println("i_user: "  + vo.getI_user());
		System.out.println("i_rest: "  + vo.getI_rest());
		System.out.println("addr: "  + vo.getAddr());
		System.out.println("nm: "  + vo.getNm());
		
		
		return ViewRef.TEMP_MAP;
	}
	
	@RequestMapping("/del")
	public String delRestaurant(RestaurantParam param, HttpSession hs) {
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		
		int result = 1;
		//try-catch가 없으면 query문이 다 보이게된다.
		try {
			service.delRestTran(param);
		}catch(Exception e) {
			result =0;
		}
		System.out.println("result : " + result);
		
		
		return "redirect:/";
	}
	
	@RequestMapping("/recMenus")
	public String recMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
		int i_rest = service.insRecMenu(mReq);
		//쿼리 스트링을 만드는 문장
		ra.addAttribute("i_rest", i_rest);
		
		return "redirect:/restaurant/detail";
	}
	
}
