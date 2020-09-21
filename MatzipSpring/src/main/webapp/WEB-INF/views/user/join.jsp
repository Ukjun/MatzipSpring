<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<div id="sectionContainerCenter">
	<div id="sectionContainer">
	<div class="error">${msg }</div>
		<form id="frm" class="frm" action="/user/join" method="post">
			<div id="idChkResult"></div>
			<div><input type="text" name="user_id" id="user_id" placeholder="아이디">
				<button type="button" id="id_btn" onclick="chkId()" value="0">아이디 중복체크</button>
			</div>
			<div><input type="password" name="user_pw" placeholder="비밀번호"></div>
			<div><input type="password" name="user_pwre" placeholder="비밀번호 확인"></div>
			<div><input type="text" name="nm" placeholder="이름"></div>
			<div><input type="submit" id="joinBtn" value="회원가입"></div>
			<div><input type="button" id="joinBtn" onclick="moveToLogin()" value="Move To Login"></div>
		</form>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
	function moveToLogin(){
		location.href = "/user/login"
	}
	
	function chkId(){
		const user_id = frm.user_id.value;
		axios.post('/user/ajaxIdChk',{
			//문자 : value 값
				user_id
		}).then(function(res){
			console.log(res);
			console.log(res.data);
			if(res.data == "2"){ // 아이디 없음
				idChkResult.innerText = "사용할수 있는 아이디입니다."
			}else if(res.data == "3"){ // 아이디 이미 있음
				idChkResult.innerText = "이미 사용하는 아이디입니다."
			}
		})
		document.getElementById('idChkResult').classList.toggle('msg');
	}
</script>

</div>

