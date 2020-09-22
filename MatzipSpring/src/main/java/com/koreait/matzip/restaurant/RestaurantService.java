package com.koreait.matzip.restaurant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	int insRest(RestaurantParam param) {
		
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
}
