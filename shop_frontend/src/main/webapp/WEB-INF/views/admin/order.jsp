<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>Admin 주문목록</title>
	<!-- Bootstrap core CSS -->
	<link href="${pageContext.servletContext.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="${pageContext.servletContext.contextPath }/assets/css/shop-admin.css" rel="stylesheet">
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
  	text-align: center;
  }
  th, td {
    border: 1px solid #444444;
  }
</style>
</head>
<body>

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
				<h1 class="mt-4">주문 관리 페이지</h1>
				<table class="table">
					<tr>
						<th>주문일</th>
						<th>주문번호</th>
						<th>주문자</th>
						<th>주소</th>
						<th>결제금액</th>
					</tr>
					<c:forEach items='${orderList }' var='vo' varStatus="status">
						<tr>
							<td>${vo.order_date }</td>
							<td><a href="${pageContext.servletContext.contextPath }/admin/orderdetail/${vo.order_no }">${vo.order_no }</a></td>
							<td>${vo.id }</td>
							<td>${vo.destination }</td>
							<td>${vo.pay }</td>
						</tr>
					</c:forEach>
				</table>
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