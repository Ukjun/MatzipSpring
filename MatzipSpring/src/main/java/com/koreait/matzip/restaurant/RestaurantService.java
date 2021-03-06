package com.koreait.matzip.restaurant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
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
	
	public int addHits(RestaurantParam param, HttpServletRequest req) {
		String ip = req.getRemoteAddr();
		System.out.println("ip: " + ip);
		int result = 0;
		int i_user = SecurityUtils.getLoginUserPk(req);
		int i_rest = param.getI_rest();
		System.out.println("i_rest: " + i_rest);
		
		ServletContext ctx = req.getServletContext();
		String currentReadUser = (String)ctx.getAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest());
		
		System.out.println("currendReadUser: " + currentReadUser);
		
		
		if(currentReadUser == null || currentReadUser.equals(ip)) {
			param.setI_user(i_user); // 내가 쓴 글이면 조회수 안올라가는걸로 쿼리문으로 막음
			//조회수 처리 작업
			result = mapper.addHits(param);
			System.out.println("addHits result:" + result);
			ctx.setAttribute(Const.CURRENT_REST_READ_IP+param.getI_rest(), ip);
		}
		
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
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
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
	
	// 추천메뉴 목록 띄우기(사진)
	public List<RestaurantRecMenuVO>selRestRecMenus(RestaurantParam param){
		return mapper.selRestRecMenus(param);
	}
	
	// 메뉴 목록 띄우기(사진)
	public List<RestaurantRecMenuVO>selRestMenus(RestaurantParam param){
		return mapper.selRestMenus(param);
	}
	// 추천메뉴 삭제
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
	
	// 메뉴삭제
	public int ajaxDelMenu(RestaurantParam param) {
		
		if(param.getMenu_pic() != null && !"".equals(param.getMenu_pic())) {
			String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";
			if(FileUtils.delFile(path+param.getMenu_pic())) {
				return mapper.ajaxDelMenu(param);
			}else {
				return Const.FAIL;
			}
		}
		return mapper.ajaxDelMenu(param);
	}

	public int insMenus(RestaurantFile param,int i_user) {
		// TODO Auto-generated method stub
		int i_rest = param.getI_rest();
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
		List<RestaurantRecMenuVO> list = new ArrayList();
		System.out.println("Const.realPath: " + Const.realPath);
		String path = Const.realPath + "/resources/img/rest/"+ param.getI_rest()+ "/menu/";
		for(MultipartFile file : param.getMenu_pic()) {
			RestaurantRecMenuVO vo = new RestaurantRecMenuVO();
			
			list.add(vo);
			
			System.out.println("진입");
			//index.jsp로 설정되어있는것을 indexController로 이동해서 설정
			String saveFileNm = FileUtils.saveFile(path, file);
			
			vo.setI_rest(param.getI_rest());
			vo.setMenu_pic(saveFileNm);
			System.out.println("path : " + path+vo.getMenu_pic());
			System.out.println("-----Success-----");	
		}
		for(RestaurantRecMenuVO vo : list) {
			mapper.insMenus(vo);
		}
		return Const.SUCCESS;
	}
	
	
	private boolean _authFail(int i_rest, int i_user) {
		RestaurantParam param = new RestaurantParam();
		param.setI_rest(i_rest);
		
		RestaurantDMI dbResult = mapper.detailRest(param);
		if(dbResult == null || dbResult.getI_user() != i_user) {
			return true;
		}
		return false;
	}
	
}
