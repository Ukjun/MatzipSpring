package com.koreait.matzip.restaurant;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.restaurant.model.RestaurantDMI;
import com.koreait.matzip.restaurant.model.RestaurantParam;
import com.koreait.matzip.restaurant.model.RestaurantRecMenuVO;
import com.koreait.matzip.restaurant.model.RestaurantVO;

@Mapper
public interface RestaurantMapper {
	public int restInsert(RestaurantVO p);
	public int insRestRecMenu(RestaurantRecMenuVO vo);
	public int insMenus(RestaurantRecMenuVO vo);
	
	public List<RestaurantDMI> selRestList(RestaurantParam p);
	public RestaurantDMI detailRest(RestaurantParam p);
	public int selRestChkUser(int param);
	
	// menu_pic 목록들을 띄워야되기 때문에 List로 반환
	public List<RestaurantRecMenuVO> selRestRecMenus(RestaurantParam param);
	public List<RestaurantRecMenuVO> selRestMenus(RestaurantParam param);
	
	
	public int delRecRestaurant(RestaurantParam p);
	public int delRestaurantMenu(RestaurantParam p);
	public int delRestaurant(RestaurantParam p);
	public int ajaxDelRecMenu(RestaurantParam vo);
	public int ajaxDelMenu(RestaurantParam vo);
	
	public int addHits(RestaurantParam p);
	
}
