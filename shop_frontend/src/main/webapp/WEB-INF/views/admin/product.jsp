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
	<link href="${pageContext.servletContext.contextPath }/assets/css/shop-admin.css" rel="stylesheet">
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
  }
  th, td {
    border: 1px solid #444444;
  }
  #pd-create-button{
  	margin-top: 10px;
   	float: right;
  }
</style>
</head>
<body>
	<c:if test="${param.result eq 'fail' }">
		<script>
			alert('상품 등록 실패');
		</script>
	</c:if>
	<div class="d-flex" id="wrapper">

		<!-- Sidebar -->
		<c:import url='/WEB-INF/views/admin/includes/sidebar.jsp'>
			<c:param name="active" value="shopping" />
		</c:import>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			
			<!-- navigation -->
			<c:import url='/WEB-INF/views/admin/includes/navigation.jsp' />
			<!-- /navigation -->

			<div class="container-fluid">
				<h1 class="mt-4">상품 관리 페이지</h1>
				<table >
					<tr>
						<th>No</th>
						<th>상품명</th>
						<th>판매가</th>
						<th>진열상태</th>
						<th>상품분류</th>
						<th>상품제조일</th>
					</tr>
					<c:forEach items='${productList }' var='productvo'>
						<tr>
							<td>${productvo.product_no }</td>
							<td>${productvo.name }</td>
							<td>${productvo.price }</td>
							<td>${productvo.display }</td>
							<td>${productvo.category_name }</td>
							<td>${productvo.manufacturing_date }</td>
							
						</tr>
					</c:forEach>
				</table>
				<button class="btn btn-primary" id="pd-create-button" 
				onclick="location.href='${pageContext.servletContext.contextPath }/admin/product/registration'">상품등록</button>
			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- Bootstrap core JavaScript -->
	<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery.min.js"></script>
	<script src="${pageContext.servletContext.contextPath }/assets/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Menu Toggle Script -->
	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>

</body>

</html>
