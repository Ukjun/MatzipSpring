<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/res/css/swiper-bundle.min.css">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="sectionContainer">
	<div>
		<c:if test="${LoginUser.i_user == data.i_user}">
			<div>
				<a href="/restaurant/restMod"><button>수정</button></a>
				<button onclick="isDel()">가게 삭제</button>

				<form id="recFrm" action="/restaurant/recMenus"
					enctype="multipart/form-data" method="post">
					<div>
						<button type="button" id="menu_btn" onclick="addRecMenu()">추천
							메뉴 추가</button>
					</div>
					<input type="hidden" name="i_rest" value="${data.i_rest }">
					<div id="recItem"></div>
					<div>
						<input type="submit" id="menu_sub" value="등록">
					</div>
				</form>
			</div>

			<h2>-메뉴-</h2>
			<div>
				<form id="menuFrm" action="/restaurant/menus"
					enctype="multipart/form-data" method="post">
					<input type="hidden" name="i_rest" value="${data.i_rest }">
					<input type="file" name="menu_pic" multiple>
					<div id="menuItem"></div>
					<div>
						<input type="submit" value="등록">
					</div>
				</form>
			</div>

		</c:if>
		<div class="recMenuContainer">
			<c:forEach items="${recommendMenuList }" var="item">
				<div class="recMenuItem" id="recMenuItem_${item.seq }">
					<div class="pic">
						<c:if test="${item.menu_pic != null && item.menu_pic != ''}">
							<img
								src="/res/img/rest/${data.i_rest }/rec_menu/${item.menu_pic}"
								id="pic_img">
						</c:if>
					</div>
					<div class="info">
						<div class="nm">${item.menu_nm }</div>
						<!-- formatNumber에서 원화표시를 할때 type을 currency로 한다 (화폐단위 바꾸고싶으면 location변경 필요) -->
						<div class="price">
							<fmt:formatNumber type="currency" value="${item.menu_price }"></fmt:formatNumber>
						</div>
					</div>
					<c:if
						test="${LoginUser.i_user == data.i_user && item.menu_pic != null}">
						<div class="delIconContainer"
							onclick="delRecMenu(${data.i_rest},${item.seq },'${item.menu_pic }')">
							<span class="material-icons">clear</span>
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
						<c:if test="${LoginUser != null }">
							<span id="favorite" class="material-icons" onclick="toggleFavorite()">
								<c:if test="${data.is_favorite ==1 }">favorite</c:if>
								<c:if test="${data.is_favorite ==0 }">favorite_border</c:if>
							</span>
						</c:if>
					</span>
				</div>
				<div class="status_branch_none"></div>
				조회수 : <span class="cnt_hit">${data.hits }</span> <span
					class="cnt_review"></span> 찜 : <span class="cnt_facvorite">${data.cnt_favorite }</span>

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
								<div id="conMenuList" class="menuList" ></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="carouselContainer">
		<div id="imgContainer">
		<div class="swiper-container">
			<div id="swiperWrapper" class="swiper-wrapper">
			</div>
			<!-- If we need pagination -->
			<div class="swiper-pagination"></div>
			
			<!-- If we need navigation buttons -->
			<div class="swiper-button-prev"></div>
			<div class="swiper-button-next"></div>
			<c:if test="${LoginUser.i_user == data.i_user}">
				<div class="imgDel">
					<span class="material-icons" onclick="delMenu()">delete</span>		
				</div>
			</c:if>
		</div>
	</div>
	<span class="material-icons" onclick="closeCarousel()">clear</span>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
	<script>

	function toggleFavorite() {
		console.log(${data.is_favorite})
		console.log('favorite : ' + favorite.innerText.trim())
		console.log('favorite : ' + (favorite.innerText.trim() == 'favorite'))
		
		let parameter = {
			params: {
				i_rest: ${data.i_rest}	
			}
		}
		
		var icon = favorite.innerText.trim()
		
		switch(icon) {
		case 'favorite':
			parameter.params.proc_type = 'del'
			break;
		case 'favorite_border':
			parameter.params.proc_type = 'ins'
			break;
		}
		
		axios.get('/user/ajaxToggleFavorite', parameter).then(function(res) {
			if(res.data == 1) {
				favorite.innerText = (icon == 'favorite' ? 'favorite_border' : 'favorite')
			}
		})
		
	}
	
	function delMenu(){
		if(!confirm('Do you want to Delete?')){return}
		
		const obj = menuList[mySwiper.realIndex]
		if(obj != undefined){
			//서버 삭제요청
			axios.get('/restaurant/ajaxDelMenu',{
				params: {
					i_rest: ${data.i_rest},
					seq : obj.seq,
					menu_pic : obj.menu_pic
				}
			}).then(function(res){
				if(res.data==1){
					menuList.splice(mySwiper.realIndex,1)
					refreshMenu()	
				}else{
					alert("Can't Delete Menu")
				}
			})
		}
		console.log('index: '+ mySwiper.activeIndex)
		
		
		
		console.log('seq:' + obj.seq)
	}
	
	
	function openCarousel(idx){
		/* carouselContainer.style.visibility = "visible"; */
		mySwiper.slideTo(idx);
		carouselContainer.style.opacity = 1
		carouselContainer.style.zIndex = 40
	}
	function closeCarousel(){
		/* carouselContainer.style.visibility = "hidden"; */
		carouselContainer.style.opacity = 0
		carouselContainer.style.zIndex = -10
	}
	var mySwiper
	
	mySwiper = new Swiper('.swiper-container', {
		  // Optional parameters
		  direction: 'horizontal',
		  loop: true,

		  // If we need pagination
		  pagination: {
		    el: '.swiper-pagination',
		  },

		  // Navigation arrows
		  navigation: {
		    nextEl: '.swiper-button-next',
		    prevEl: '.swiper-button-prev',
		  }
		})
	var isMe = ${LoginUser.i_user == data.i_user}
	
	var menuList = []
	
	function ajaxSelMenuList(){
		axios.get('/restaurant/ajaxSelMenuList',{
			params:{
				i_rest : ${data.i_rest}
			}
		}).then(function(res){
			menuList = res.data
			refreshMenu()
		})
	}
	
	function refreshMenu(){
		
		conMenuList.innerHTML =''
		swiperWrapper.innerHTML =''
		
		menuList.forEach(function(item,idx){
			makeMenuItem(item,idx)
		})
	}
	
	function makeMenuItem(item,idx){
		//메인 화면에서 메뉴 이미지 디스플레이
		const div = document.createElement('div')
		div.setAttribute('class','menuItem')
		
		
		const img = document.createElement('img')
		img.setAttribute('src',`/res/img/rest/${data.i_rest}/menu/\${item.menu_pic}`)
		img.style.cursor = 'pointer'
		img.addEventListener('click',function(){
			openCarousel(idx+1)
		})
		
		div.append(img)
		
		
		conMenuList.append(div)
		//메뉴화면에서 디스플레이 end
		
		//팝업 화면에서 메뉴 이미지 디스플레이 start
		const swiperDiv = document.createElement('div')
		swiperDiv.setAttribute('class','swiper-slide')
		
		
		const swiperImg = document.createElement('img')
		swiperImg.setAttribute('src',`/res/img/rest/${data.i_rest}/menu/\${item.menu_pic}`)
		
		
		swiperDiv.append(swiperImg)
		
		mySwiper.appendSlide(swiperDiv);
	}
	
	
	
	var idx = 0;
	console.log(`${LoginUser.i_user}`)
	console.log(`${data.i_user}`)
		

	function isDel(){
		if(confirm('Do you want Delete?')){
			location.href = '/restaurant/del?i_rest=${data.i_rest}'
		}
	}
	
	function addRecMenu(){
		// Ctrl+C Ctrl+V 조심하기
		var div = document.createElement('div');
		div.setAttribute('id','recMenu_' + idx++)
		
		var inputNm = document.createElement('input');
		inputNm.setAttribute("type","text")
		//생성된 input 이름 설정
		inputNm.setAttribute('name','menu_nm')
		var inputPrice = document.createElement('input');
		inputPrice.setAttribute("type","number")
		inputPrice.setAttribute('name','menu_price')
		
		inputPrice.value='0'
		
		var inputPic = document.createElement('input');
		inputPic.setAttribute("type","file")
		inputPic.setAttribute('name','menu_pic')
		
		var delBtn = document.createElement('input')
		delBtn.setAttribute('type','button')
		delBtn.setAttribute('value','X')
		delBtn.addEventListener('click',function(){
			div.remove();
			console.log('idx:' + idx);
		})
		
		div.append('메뉴 : ')
		div.append(inputNm)
		div.append('가격 : ')
		div.append(inputPrice)
		div.append('사진 : ')
		div.append(inputPic)
		div.append(delBtn)
		//div id 이름 (메뉴를 다중으로 넣기위해서 )
		recItem.append(div)
	}
	
	
	
	function delRecMenu(i_rest,seq,fileNm){
		console.log("i_rest: " + i_rest)
		console.log("seq : " + seq)
		
		//여기서 이엘식 적는것은 고정값을 지정하는 것 
		axios.get('/restaurant/ajaxDelRecMenu',{
			params:{
				'i_rest' : ${data.i_rest}, 
				seq, 
				fileNm,
				'i_user' : ${data.i_user}
			}
		}).then(function(res){
			if(res.data==1){
				//element 삭제
				console.log(res.data)
				var ele = document.querySelector('#recMenuItem_' + seq)
				ele.remove();
			}
		})
	}
	addRecMenu()
	
	ajaxSelMenuList()
	</script>
</div>
