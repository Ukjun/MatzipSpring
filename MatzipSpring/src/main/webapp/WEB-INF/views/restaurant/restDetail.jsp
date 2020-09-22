<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap"
	rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="sectionContainer">
	<div>
		<c:if test="${LoginUser.i_user == data.i_user}">
			<div>
				<a href="/restaurant/restMod"><button>수정</button></a>
				<button onclick="isDel()">가게 삭제</button>
				
				<form id="recFrm" action="/restaurant/addRecMenus" enctype="multipart/form-data" method = "post">
					<div><button type="button" id="menu_btn" onclick="addRecMenu()">추천 메뉴 추가</button></div>
					<input type="hidden" name="i_rest" value="${data.i_rest }">
					 <div id="recItem">
					</div> 
					<div><input type="submit" id="menu_sub" value = "등록"></div>
				</form>
			</div>
			
			<h2>-메뉴-</h2>
			<div>
				<form id="menuFrm" action="/restaurant/addMenuProc" enctype="multipart/form-data" method = "post">
					<input type="hidden" name="i_rest" value="${data.i_rest }">
					<input type="file" name="menu_pic" multiple>
					<div id="menuItem"></div>
					<div><input type="submit" value="등록"></div>
				</form>
			</div>
			
		</c:if>
		<div class="recMenuContainer">
			<c:forEach items="${recommendMenuList }" var ="item">
				<div class="recMenuItem" id="recMenuItem_${item.seq }">
					<div class="pic">
						<c:if test="${item.menu_pic != null && item.menu_pic != ''}">
							<img src ="/res/img/restaurant/${data.i_rest }/${item.menu_pic}" id="pic_img">
						</c:if>
					</div>
					<div class="info">
						<div class="nm">${item.menu_nm }</div>
						<div class="price"><fmt:formatNumber type="number" value = "${item.menu_price }"></fmt:formatNumber></div>
					</div>
					<c:if test="${LoginUser.i_user == data.i_user && item.menu_pic != null}">
						<div class="delIconContainer" onclick="delRecMenu(${data.i_rest},${item.seq },'${item.menu_pic }')">
							<span class= "material-icons">clear</span>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
		<div class="recMenuContainer">
			<c:forEach items="${menuList }" var ="item">
				<div class="recMenuItem" id="recMenuItem_${item.seq }">
					<div class="pic">
						<c:if test="${item.menu_pic != null && item.menu_pic != ''}">
							<img src ="/res/img/restaurant/${data.i_rest }/menu/${item.menu_pic}" id="pic_img">
						</c:if>
					</div>
					<c:if test="${LoginUser.i_user == data.i_user && item.menu_pic != null}">
						<div class="delIconContainer" onclick="delMenu(${data.i_rest},${item.seq },'${item.menu_pic }')">
							<span class= "material-icons">clear</span>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
		
		<div>가게사진들</div>
		<div class="restaurant-detail">
			<div id="detail-header">
				<div class="restaurant_title_wrap">
					<span class="title">
						<h1 class="restaurant_name">가게 이름 : ${data.nm }</h1>
					</span>
				</div>
				<div class="status_branch_none"></div>
				조회수 : <span class="cnt_hit">${data.hits }</span> 
				<span class="cnt_review"></span> 
				찜 : <span class="cnt_facvorite">${data.cnt_favorite }</span>

			</div>
			<div>
				<table>
					<caption>레스토랑 상세정보</caption>
					<tbody>
						<tr>
							<th>주소</th>
							<td>${data.addr}</td>
						</tr>
						<tr>
							<th>카테고리</th>
							<td>${data.cd_category_nm }</td>
						</tr>
						<tr>
							<th>메뉴</th>
							<td>
							<div class="menuList">
							<c:if test="${fn:length(menuList)>0 }">
								<c:forEach var="i" begin="0" end="${fn:length(menuList) > 3 ? 2 : fn:length(menuList) - 1}">
											<div class="menuItem">
												<img src="/res/img/restaurant/${data.i_rest}/menu/${menuList[i].menu_pic}">
											</div>
								</c:forEach>
							</c:if>
								<c:if test="${fn:length(menuList) > 3}">
									<div class="menuItem bg_black">
										<div class="moreCnt">
											+${fn:length(menuList) - 3}
										</div>
									</div>
								</c:if>
							</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script>
	
	
	var idx = 0;
	
	console.log(`${LoginUser.i_user}`)
	console.log(`${data.i_user}`)
		function isDel(){
			if(confirm('Do you want Delete?')){
				location.href = '/restaurant/restDel?i_rest=${data.i_rest}'
			}
		}
	function addMenu(){
		
	}
	addMenu()
	function addRecMenu(){
		// Ctrl+C Ctrl+V 조심하기
		var div = document.createElement('div');
		
		var inputNm = document.createElement('input');
		inputNm.setAttribute("type","text")
		//생성된 input 이름 설정
		inputNm.setAttribute('name','menu_nm')
		var inputPrice = document.createElement('input');
		inputPrice.setAttribute("type","number")
		inputPrice.setAttribute('name','menu_price')
		var inputPic = document.createElement('input');
		inputPic.setAttribute("type","file")
		inputPic.setAttribute('name','menu_pic_' + idx++)
		
		
		div.append('메뉴 : ')
		div.append(inputNm)
		div.append('가격 : ')
		div.append(inputPrice)
		div.append('사진 : ')
		div.append(inputPic)
		//div id 이름 (메뉴를 다중으로 넣기위해서 )
		recItem.append(div)
	}
	addRecMenu()
	
	
	function delRecMenu(i_rest,seq,fileNm){
		console.log("i_rest: " + i_rest)
		console.log("seq : " + seq)
		
		//여기서 이엘식 적는것은 고정값을 지정하는 것 
		axios.get('/restaurant/ajaxDelRecMenu',{
			params:{
				i_rest, 
				seq, 
				fileNm,
				'i_user' : ${data.i_user}
			}
		}).then(function(res){
			if(res.data.result==1){
				//element 삭제
				var ele = document.querySelector('#recMenuItem_' + seq)
				ele.remove();
			}
		})
	}
	function delMenu(i_rest,seq,fileNm){
		console.log("i_rest: " + i_rest)
		console.log("seq : " + seq)
		
		//여기서 이엘식 적는것은 고정값을 지정하는 것 
		axios.get('/restaurant/ajaxDelMenu',{
			params:{
				i_rest, 
				seq, 
				fileNm,
				'i_user' : ${data.i_user}
			}
		}).then(function(res){
			if(res.data.result==1){
				//element 삭제
				var ele = document.querySelector('#recMenuItem_' + seq)
				ele.remove();
			}
		})
	}
	
	</script>
</div>