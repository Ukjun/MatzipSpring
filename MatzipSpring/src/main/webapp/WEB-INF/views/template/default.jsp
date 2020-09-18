<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/res/css/common.css">

<title>${title}</title>
</head>
<body>
	<div id ="container">
		<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
	</div>
</body>
</html>