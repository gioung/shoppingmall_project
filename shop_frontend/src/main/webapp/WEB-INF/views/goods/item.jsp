<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>Shop Homepage - Start Bootstrap Template</title>
	<!-- Bootstrap core CSS -->
	<link href="${pageContext.servletContext.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="${pageContext.servletContext.contextPath }/assets/css/shop-item.css" rel="stylesheet">
<script>
function addCart(){
	var param = $("form[name=cartvalue]").serialize();
	console.log(param);
	 $.ajax({	
				url : "${pageContext.servletContext.contextPath }/cart",
				type : "post",
				cache : false,
				dataType : "json",
				data : param,
				success : function(response) {
					console.log(response.data);
				},
				error :function(data){
				    alert("error");
				  }
			});
	 
}
</script>
</head>

<body>
	<!-- Navigation -->
	<c:import url='/WEB-INF/views/includes/navigation.jsp'>
		<c:param name="active" value="shopping" />
	</c:import>
	<!-- /.Navigation -->

	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<!-- 카테고리 include -->
			<c:import url="/WEB-INF/views/includes/category.jsp"/>
			<!-- /.col-lg-3 -->
			
			<div class="col-lg-9">
				<form action="" method="post" name="cartvalue">
				<div class="card mt-4">
					<img class="card-img-top img-fluid"
						src="${pageContext.servletContext.contextPath }/assets/image/${product.image }" alt="이미지">
					<div class="card-body">
						<input type="hidden" name="product_no" value="${product.product_no }">
						<h3 class="card-title">${product.name }</h3>
						<h4>${product.price }</h4>
						<b>옵션</b>  <select name='pd_detail_no'>
						  <option value='' selected>-- 선택 --</option>
						  <c:forEach items='${productDetailList }' var='vo' varStatus='status'>
						  	<option value='${vo.pd_detail_no }'>${vo.option }</option>
						  </c:forEach>
						</select>
						<p></p>
						<label style="border-top: 10px">수량 <input type="number" name="qty" min="1" max="99" ></label>
						<p class="card-text">
							${product.detail_desc }
						</p>
						<div>
							<input type="submit" value="주문하기">
							<button type="button" onclick="addCart()">장바구니 담기</button>
						</div>
					</div>
				</div>
				</form>
			</div>
			<!-- /.col-lg-9 -->

		</div>

	</div>
	<!-- /.container -->

	<!-- Footer -->
	<c:import url='/WEB-INF/views/includes/footer.jsp' />
	<!-- /.Footer -->
</body>

</html>