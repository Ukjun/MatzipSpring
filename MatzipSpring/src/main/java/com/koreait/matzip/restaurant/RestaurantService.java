package com.koreait.matzip.restaurant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.restaurant.model.RestaurantDMI;
import com.koreait.matzip.restaurant.model.RestaurantParam;

@Service
public class RestaurantService {
	@Autowired
	private RestaurantMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	public List<RestaurantDMI> selRestList(RestaurantParam param){
		return mapper.selRestList(param);
	}
	
	public int insRest(RestaurantParam param) {
		
		System.out.println("i_user:" + param.getI_user());
		int result = mapper.restInsert(param);
		System.out.println("insert Rest result: " + result);
		
		return result;
	}
	
	public List<CodeVO> selCategoryList(){
		CodeVO p = new CodeVO();
		p.setI_m(1); // 음식점 카테고리 코드 = 1
		
		return cMapper.selCodeList(p);
	}
	
	
	public RestaurantDMI detailRest(RestaurantParam param) {
		return mapper.detailRest(param);
	}
	
	public int addHits(RestaurantParam param) {
		int result = mapper.addHits(param);
		System.out.println("addHits result:" + result);
		
		return result;
	}
	
	@Transactional
	public void delRestTran(RestaurantParam param) {
		mapper.delRecRestaurant(param);
		mapper.delRestaurant(param);
		mapper.delRestaurantMenu(param);
	}
	
	
	public int delRecRestaurant(RestaurantParam param) {
		int result = mapper.delRecRestaurant(param);
		System.out.println("Recommend del result :" + result);
		return result;
	}
	public int delRestaurantMenu(RestaurantParam param) {
		int result = mapper.delRestaurantMenu(param);
		System.out.println("Menu del result :" + result);
		return result;
	}
	public int delRestaurant(RestaurantParam param) {
		int result = mapper.delRestaurant(param);
		System.out.println("Restaurant del result :" + result);
		return result;
	}
	
	
	
}
