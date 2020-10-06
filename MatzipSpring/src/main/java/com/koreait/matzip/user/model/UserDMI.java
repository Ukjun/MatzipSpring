package com.koreait.matzip.user.model;

import java.util.List;

import com.koreait.matzip.restaurant.model.RestaurantRecMenuVO;

public class UserDMI extends UserVO{
	private int i_rest;
	private List<RestaurantRecMenuVO> menuList;
	private String main_pic;
	private String rest_nm;
	private String rest_addr;
	
	
	public String getRest_nm() {
		return rest_nm;
	}
	public void setRest_nm(String rest_nm) {
		this.rest_nm = rest_nm;
	}
	public String getRest_addr() {
		return rest_addr;
	}
	public void setRest_addr(String rest_addr) {
		this.rest_addr = rest_addr;
	}
	public String getMain_pic() {
		return main_pic;
	}
	public void setMain_pic(String main_pic) {
		this.main_pic = main_pic;
	}
	public int getI_rest() {
		return i_rest;
	}
	public void setI_rest(int i_rest) {
		this.i_rest = i_rest;
	}
	public List<RestaurantRecMenuVO> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<RestaurantRecMenuVO> menuList) {
		this.menuList = menuList;
	}
}
