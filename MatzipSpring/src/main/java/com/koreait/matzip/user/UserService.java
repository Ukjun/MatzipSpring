package com.koreait.matzip.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.restaurant.RestaurantMapper;
import com.koreait.matzip.restaurant.model.RestaurantParam;
import com.koreait.matzip.restaurant.model.RestaurantRecMenuVO;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserParam;
import com.koreait.matzip.user.model.UserVO;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private RestaurantMapper restMapper;
	
	// 1. 성공, 2. 아이디없음, 3.비번 틀림 
	// DTO : parameter / DMI : Select 
	public int login(UserParam param) {
		if(param.getUser_id().equals("")) {
			System.out.println(param.getUser_id().equals(""));
			return Const.NO_ID;
		}
		UserDMI dbUser = mapper.selUser(param);
		if(dbUser == null) { return Const.NO_ID;};
		System.out.println("db pw: " + dbUser.getUser_pw());
		
		String encryPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		System.out.println("encryPw:" + encryPw);
		
		if(!encryPw.equals(dbUser.getUser_pw())) {
			return Const.NO_PW;
		}
		param.setI_user(dbUser.getI_user());
		param.setUser_pw(null);
		param.setNm(dbUser.getNm());
		param.setProfile_img(dbUser.getProfile_img());
		return Const.SUCCESS;
	}

	public int join(UserVO param) {
		// TODO Auto-generated method stub
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.insUser(param);
		
	}

	public int ajaxToggleFavorite(UserParam param) { //i_rest i_user proc_type
		// TODO Auto-generated method stub
		System.out.println(param.getProc_type());
		switch(param.getProc_type()) {
		case "ins":
			return mapper.insFavorite(param);
		case "del":
			return mapper.delFavorite(param);
		}
		
		return 0;
	}
	
	public List<UserDMI> selFavoriteList(UserParam param){
		List<UserDMI> list = mapper.selFavoriteList(param);
		for(UserDMI vo : list) {
			RestaurantParam param2 = new RestaurantParam();
			//restaurantParam에 있는 i_rest값 들고와서 박기
			param2.setI_rest(vo.getI_rest());
			
			//그 i_rest값을 들고 리스트 뽑아내고 UserDMI list에 박아버리기
			List<RestaurantRecMenuVO> eachRecMenuList = restMapper.selRestRecMenus(param2);
			vo.setMenuList(eachRecMenuList);
			
			
		}
		
		
		return list;
	}
}
