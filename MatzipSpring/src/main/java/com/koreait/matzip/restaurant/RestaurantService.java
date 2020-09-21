package com.koreait.matzip.restaurant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.koreait.matzip.restaurant.model.RestaurantDMI;
import com.koreait.matzip.restaurant.model.RestaurantParam;

@Service
public class RestaurantService {
	@Autowired
	private RestaurantMapper mapper;
	
	String selRestList(RestaurantParam param){
		List<RestaurantDMI> list = mapper.selRestList(param);
		if(list!=null) {
			System.out.println("list size:" + list.size());
			Gson gson = new Gson();
			return gson.toJson(list);
		}
		return "";
	}
	
	int insRest(RestaurantParam param) {
		
		System.out.println("i_user:" + param.getI_user());
		int result = mapper.restInsert(param);
		System.out.println("insert Rest result: " + result);
		
		return result;
	}
}
