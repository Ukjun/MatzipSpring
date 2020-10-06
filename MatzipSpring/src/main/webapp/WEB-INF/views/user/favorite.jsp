<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<c:forEach items="${data }" var="item">
		<div class="f-item">
			<div class="img">
				<img
					src="/img/src/rest/${item.i_rest }/menu/${item.menuList[0].menu_pic}">
			</div>
			<div class="ctnt">
				<div class="top">
					<div class="f-title">
						<div class="nm font-NotoSans-500">은정분식</div>
						<span>대구시 달서구 어쩌고 저쩌고</span>
					</div>
					<div class="f-fav">
						<span class="material-icons">favorite</span>
					</div>
				</div>
				<div class="bottom">
					<div class="recMenuItem">
						<div class="pic">
							<img
								src="/res/img/rest/17/menu/788416d2-332c-471b-aadb-9786500fd04f.gif">
						</div>
						<div class="info">
							<div class="nm">00</div>
							<div class="price">00</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	<div class="f-item">
		<div class="img"></div>
		<div class="ctnt">--1</div>
	</div>

	<div class="f-item">
		<div class="img"></div>
		<div class="ctnt">--2</div>
	</div>
</div>