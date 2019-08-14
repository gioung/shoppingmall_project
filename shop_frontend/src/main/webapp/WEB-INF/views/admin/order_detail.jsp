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
	<title>Admin 주문상세목록</title>
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
				<h2 class="mt-4">주문정보</h2>
				<table class="table">
					<tr>
						<th>주문번호</th>
						<th>주문자</th>
						<th>주문자 연락처</th>
						<th>받는분</th>
						<th>받는분 번호</th>
						<th>배송지</th>
					</tr>
						<tr>
							<td>${orderVo.order_no }</td>
							<td>${orderVo.ord_name }</td>
							<td>${orderVo.ord_tel }</td>
							<td>${orderVo.recv_name }</td>
							<td>${orderVo.recv_tel }</td>
							<td>${orderVo.destination }</td>
						</tr>
				</table>
				<hr>
				<h2 class="mt-4">주문목록</h2>
				<table class="table">
					<tr>
						<th>상품명</th>
						<th>상품옵션</th>
						<th>상품가격</th>
					</tr>
					<c:forEach items='${orderedProductList }' var='vo' varStatus="status">
						<tr>
							<td>${vo.product_name }</td>
							<td>${vo.option }</td>
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