package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
	// 1. 성공, 2. 아이디없음, 3.비번 틀림 
	public int login(UserDTO param) {
		if(param.getUser_id().equals("")) {
			return Const.NO_ID;
		}
		UserDMI dbUser = mapper.selUser(param);
		System.out.println("db pw: " + dbUser.getUser_pw());
		
		String salt = dbUser.getSalt();
		String encryPw = SecurityUtils.getEncrypt(param.getUser_pw(), salt);
		System.out.println("encryPw:" + encryPw);
		
		if(encryPw.equals(dbUser.getUser_pw())) {
			return Const.SUCCESS;
		}else
			return Const.NO_PW;	
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
