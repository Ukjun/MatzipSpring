<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="/res/css/common.css">
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap"
	rel="stylesheet">
<div id="sectionContainerCenter">
	<div>
		<form id="frm" action="/restaurant/restregProc" method="post"
			 onsubmit="return chkFrm()">
			<div>
				<input type="text" name="nm" placeholder="가게명">
			</div>
			<div>
				<input type="text" name="addr" onkeyup="changeAddr()"
					placeholder="주소">
			</div>
			<div>
				<button type="button" onclick="getLatLng()">좌표 가져오기</button><span id="resultGetLating"></span>
			</div>
			<input type="hidden" name="lat" value="0"> 
			<input type="hidden" name="lng" value="0">
			<div>
				카테고리 : 
				<select name="cd_category">
					<option value="0">--선택--</option>
					<c:forEach items="${categoryList }" var="item">
						<option value="${item.cd }">${item.val }</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<input type="submit" value="등록">
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=dc267f04a16124aede3d52e9e1efb762&libraries=services"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script>
	console.log(`${LoginUser.i_user}`)
		var geocoder = new kakao.maps.services.Geocoder();
		function chkFrm(){
			if(frm.nm.value.length==0){
				alert('가게명을 입력해주세요')
				frm.nm.focus()
				return false
			} else if(frm.addr.value.length <9){
				alert('주소를 확인해주세요')
				frm.addr.focus()
				return false
			} else if(frm.lat.value=='0' || frm.lng.value =='0'){
				alert('좌표를 가져와주세요')
				frm.lat.focus()
				return false
			} else if(frm.cd_category.value =='0'){
				alert('카테고리를 선택하세요')
				frm.cd_category.focus()
				return false
			}
		}
		
		function changeAddr(){
			resultGetLating.innerText = ""
			frm.lat.value = "0";
			frm.lng.value = "0";
		}
		
		function getLatLng(){
			const addStr = frm.addr.value;
			
			if(addStr.length < 9){
				alert("Please Check Your Address")
				frm.addr.focus();
				return
			}
			
			geocoder.addressSearch(addStr, function(result,status){
				if (status === kakao.maps.services.Status.OK) {
			        console.log(result[0]);
			        
			        
			        if(result.length>0){
			        	resultGetLating.innerText = "V"
			        	frm.lat.value = result[0].y;
				       	frm.lng.value = result[0].x;	
			        }
			       	
			    }
			});
		}

	</script>
</div>