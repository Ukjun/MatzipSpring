package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.koreait.matzip.user.model.UserParam;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
		
		System.out.println("interceptor");
		HttpSession hs = request.getSession();
		UserParam loginUser = (UserParam)hs.getAttribute(Const.LOGIN_USER);
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		String[] uriArr = uri.split("/");
		boolean isLogout = (loginUser == null);
		System.out.println("isLogout: " + isLogout);
		
		System.out.println("uriArr length : " + uriArr.length);
		if(uriArr[1].equals("res")) { // 리소스(js,css,img)
			return true;
		}
		else if(uriArr.length<3) { // 주소가 이상한 경우
			return false;
		}
		
		switch (uriArr[1]) {
		case ViewRef.URI_USER:
			switch(uriArr[2]) {
			case "login": case"join":
				if(!isLogout) { // 로그인 되어있는 상태
					response.sendRedirect("/restaurant/map");
					return false;
				}
			}
		case ViewRef.URI_RESTURANT:
			switch(uriArr[2]) {
			case "restReg": case "map":
				if(isLogout) { // 로그아웃상태
					response.sendRedirect("/user/login");
					return false;
				}
			}
		}
		return true;
		
	}
}
