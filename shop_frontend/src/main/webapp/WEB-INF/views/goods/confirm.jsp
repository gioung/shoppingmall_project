<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#wrapper{
		display: table;
	}
	
	#content{
		display: table-cell;
		vertical-align: middle;
	}
	div.button
	{
		margin: auto;
		width: 50%;
	}
</style>
<script>
	function no(){
		self.close();
	}
</script>
</head>
<body>
<div class="title">
	<h1>확인창</h1>
	<div id="wrapper">
		<div id="content">
			물건을 장바구니에 담았습니다.<br>
			장바구니를 확인하시겠습니까?<br>	
		</div>
	</div>
	<div>
		<button type="button" onclick="location.href='${pageContext.servletContext.contextPath }/user/cart'">장바구니 보기</button>
		<button type="button" onclick="no()">취소</button>
	</div>
</div>
<div>
	
</div>
</body>	
</html>