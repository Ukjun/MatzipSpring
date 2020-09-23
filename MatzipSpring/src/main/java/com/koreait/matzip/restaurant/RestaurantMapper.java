package com.koreait.matzip.restaurant;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.restaurant.model.RestaurantDMI;
import com.koreait.matzip.restaurant.model.RestaurantParam;
import com.koreait.matzip.restaurant.model.RestaurantVO;

@Mapper
public interface RestaurantMapper {
	public int restInsert(RestaurantVO p);
	public List<RestaurantDMI> selRestList(RestaurantParam p);
	public RestaurantDMI detailRest(RestaurantParam p);
	public int delRecRestaurant(RestaurantParam p);
	public int delRestaurantMenu(RestaurantParam p);
	public int delRestaurant(RestaurantParam p);
	public int addHits(RestaurantParam p);
}
