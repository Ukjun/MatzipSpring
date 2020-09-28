package com.koreait.matzip;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.koreait.matzip.user.model.UserVO;

public class SecurityUtils {
	public static int getLoginUserPk(HttpServletRequest request) {
		UserVO LoginUser =  (UserVO)request.getAttribute(Const.LOGIN_USER);
		return LoginUser == null? 0: LoginUser.getI_user();
	}
	
	public static int getLoginUserPk(HttpSession hs) {
		UserVO LoginUser =  (UserVO)hs.getAttribute(Const.LOGIN_USER);
		return LoginUser == null? 0: LoginUser.getI_user();
	}
	
	public static UserVO getLoginUser(HttpServletRequest request) { // 로그인 유저 확인
		HttpSession hs = request.getSession();
		return(UserVO)hs.getAttribute(Const.LOGIN_USER);
	}
	
	public static boolean isLogout(HttpServletRequest request) { // 로그아웃 확인 
		return getLoginUser(request)==null; // 세션으로 넘어오지않는 경우 true 반환
	}
	
	
	public static String generateSalt() {
		return BCrypt.gensalt();
	}

	public static String getEncrypt(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
}
