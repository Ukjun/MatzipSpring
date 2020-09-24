package com.koreait.matzip.restaurant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.restaurant.model.RestaurantDMI;
import com.koreait.matzip.restaurant.model.RestaurantFile;
import com.koreait.matzip.restaurant.model.RestaurantParam;
import com.koreait.matzip.restaurant.model.RestaurantRecMenuVO;

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
	//에러가 터지면 rollback 에러가 터지지않고 실행이되면 commit
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
	
	//받고 Detail로 이동
	public int insRecMenu(MultipartHttpServletRequest mReq) {
		
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menunmArr = mReq.getParameterValues("menu_nm");
		String[] pricenmArr = mReq.getParameterValues("menu_price");
		String path = mReq.getServletContext().getRealPath("/resources/img/rest/"+ i_rest + "/rec_menu/");
		
		
		List<RestaurantRecMenuVO> list = new ArrayList();
		for(int i=0; i<menunmArr.length; i++) {
			RestaurantRecMenuVO vo = new RestaurantRecMenuVO();
			list.add(vo);
			
			String menu_nm = menunmArr[i];
			int menu_price = CommonUtils.parseStringToInt(pricenmArr[i]);
			vo.setI_rest(i_rest);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			
			//파일 저장			
			MultipartFile mf = fileList.get(i);
			
			if(mf.isEmpty()) {continue;} //파일이 없으면 스킵
			
			System.out.println("진입");
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			
			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
		for(RestaurantRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}
	return i_rest;	
	}
	public List<RestaurantRecMenuVO>selRestRecMenus(RestaurantParam param){
		return mapper.selRestRecMenus(param);
	}
	public List<RestaurantRecMenuVO>selRestMenus(RestaurantParam param){
		return mapper.selRestMenus(param);
	}
	
	public int ajaxDelRecMenu(RestaurantParam param,String realPath) {
		//파일 삭제
		List<RestaurantRecMenuVO> list = mapper.selRestRecMenus(param);
		System.out.println("RecMenuList Size : " + list.size());
		
		if(list.size()==1) {
			RestaurantRecMenuVO item = list.get(0);
			
			System.out.println("realPath : " + realPath);
			if(item.getMenu_pic()!=null && !item.getMenu_pic().equals("")) {
				File file = new File(realPath+item.getMenu_pic());
				if(file.exists()) {
					if(file.delete()) {
						System.out.println("----File Delete Success----");
						return mapper.ajaxDelRecMenu(param);
					}else {
						System.out.println("----File Delete Fail----");
					}
				}else {
					System.out.println("Not Found File");
				}
			}
		}
		return mapper.ajaxDelRecMenu(param);
	}

	public void insMenus(RestaurantFile param,HttpSession hs) {
		// TODO Auto-generated method stub
		int i_rest = param.getI_rest();
		
		List<RestaurantRecMenuVO> list = new ArrayList();
		
		
		for(MultipartFile file : param.getMenu_pic()) {
			RestaurantRecMenuVO vo = new RestaurantRecMenuVO();
			String path = hs.getServletContext().getRealPath("/resources/img/rest/"+ i_rest + "/menu/");
			
			list.add(vo);
			if(file.isEmpty()) {return;}
			System.out.println("진입");
			vo.setI_rest(i_rest);
			String originFileNm = file.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			
			try {
				file.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
				System.out.println("path : " + path+vo.getMenu_pic());
				System.out.println("-----Success-----");
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		for(RestaurantRecMenuVO vo : list) {
			mapper.insMenus(vo);
		}
	}
	
}
