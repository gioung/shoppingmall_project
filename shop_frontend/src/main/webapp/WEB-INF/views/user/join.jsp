<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>Shop Homepage - Start Bootstrap Template</title>
	<!-- Bootstrap core CSS -->
	<link href="${pageContext.servletContext.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="${pageContext.servletContext.contextPath }/assets/css/shop-login.css" rel="stylesheet">
	<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
	<script>
		function certification(){
			var email = $('#email')[0].value;
			$.ajax({	
				url : "${pageContext.servletContext.contextPath }/user/checkemail?email="+email,
				type : "get",
				success : function(response) {
					console.log(response.result);
					if(response.result == "success"){
						alert("사용 가능한 아이디 입니다.");
						$('#id-certification')[0].disabled = true;
						$('#submit')[0].disabled = false;
					}
					else if(response.result == "fail"){
						alert("사용할 수 없는 아이디 입니다.");
					}
				},
				error :function(data){
				    alert("error");
				  }
			});
		}
		
		function resetCertification(){
			$('#id-certification')[0].disabled = false;
			$('#submit')[0].disabled = true;
		}
		
		
		
	</script>
</head>
<body>
	<!-- Navigation -->
	<c:import url='/WEB-INF/views/includes/navigation.jsp'>
		<c:param name="active" value="login" />
	</c:import>
	<!-- /.Navigation -->
	<c:if test="${param.result eq 'fail' }">
	<script>
		alert('유효하지 않은 데이터입니다.')
	</script>
	</c:if>
 	<div class="container" id="join-form">
 		<h1>회원 가입</h1>
 		<form method="POST"	action="${pageContext.servletContext.contextPath }/user/join">

					<label class="block-label" for="name">이름</label> 
					<input id="name" class="inputs" name="name" type="text" value="" placeholder="이름을 입력하세요">
					
					<label class="block-label" for="email">이메일</label> 
					<input id="email" class="inputs" name="email" type="email" value="" placeholder="이메일을 입력하세요" onchange="resetCertification()">	
					<button id="id-certification" onclick="certification()" type="button">id 중복체크</button>
					
					<label class="block-label">패스워드</label> 
					<input class="inputs" name="password"type="password" value="" placeholder="패스워드를 입력하세요.">

					<label class="block-label">연락처</label>
					<input class="inputs" type="tel" name="phone_num" id="phone_num"  placeholder="연락처를 입력하세요.">
					
					<label class="block-label">생일</label>	 
		            <input type="date" name="birth" id="birth">
		             
					<label class="block-label">Gender:</label>
				
					<div class="col-xs-4 male">	 
				    	 <input type="radio" name="gender"  id="gender" value="M">Male
				 	</div>
				 
				 	<div class="col-xs-4 female">
				    	 <input type="radio"  name="gender" id="gender" value="F" >Female
			     	</div>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y" required>
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input id="submit" type="submit" value="가입하기" disabled="disabled">

				</form>
	</div>
	<!-- /.container -->

	<!-- Footer -->
	<c:import url='/WEB-INF/views/includes/footer.jsp' />
	<!-- /.Footer -->
</body>
</html>