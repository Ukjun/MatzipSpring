<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<c:forEach items="${css }" var="item">
		<link rel="stylesheet" type="text/css" href="/res/css/${item }.css">
</c:forEach>
<link rel="stylesheet" type="text/css" href="/res/css/common.css">
</head>
<body>
	<div id="container">
		<header>
			<div id="headerLeft">
			<c:if test="${LoginUser !=null }">
				<div class="containerPImg">
					<c:choose>
						<c:when test="${LoginUser.profile_img != null}">
							<img class="pImg" onclick="window.open(this.src)"
								src="/res/img/user/${LoginUser.i_user }/${LoginUser.profile_img}">
						</c:when>
						<c:otherwise>
							<img class="pImg" onclick="window.open(this.src)"
								src="/res/img/default_profile.jpg">
						</c:otherwise>
					</c:choose>

				</div>
				<div class="ml5">${LoginUser.nm}님환영합니다.</div>
				<div class='ml15' id="headerLogout">
					<a href="/user/logout">로그아웃</a>
				</div>
				</c:if>
				<c:if test="${LoginUser == null }">
					<div class="ml15" id="headerLogout"><a href="/user/login">Login</a></div>
				</c:if>
			</div>
			<div id="headerRight">
				<a href="/restaurant/map">지도</a>
				<a class="ml15" href="/restaurant/restReg">등록</a> 
				<a class="ml15" href="/user/favorite">찜</a>
			</div>
		</header>
		<section>
			<jsp:include page="/WEB-INF/views/${view }.jsp"></jsp:include>
		</section>
		<footer> 회사 정보 </footer>
	</div>
</body>
</html>