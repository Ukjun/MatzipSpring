package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserParam;
import com.koreait.matzip.user.model.UserVO;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
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
}
