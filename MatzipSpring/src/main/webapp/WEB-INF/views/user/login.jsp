<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">

<div id="sectionContainerCenter">
	<div>
		<div id="msg">${data.msg }</div>
		<form class="frm" action="/user/login" method="post">
			<div>
				<input type="text" name="user_id" placeholder="아이디" value="${data.user_id }">
			</div>
			<div>
				<input type="password" name="user_pw" placeholder="비밀번호">
			</div>
			<div>
				<input type="submit" value="로그인">
			</div>
			<div><input type="button" id="joinLogin" onclick="moveToJoin()" value="Move To Join"></div>
		</form>
	</div>
</div>
<script>
console.log(`${data.msg}`)
	function moveToJoin(){
		location.href = "/user/join"
	}
</script>

