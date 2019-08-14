<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap core CSS -->
<link href="${pageContext.servletContext.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="${pageContext.servletContext.contextPath }/assets/css/shop-item.css" rel="stylesheet">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
<title>주문페이지</title>
<style>
	table td img {
  	width: 70px;
  	height: 70px;
  } 
</style>
<script>
	function recvActive(state){
			console.log(state);
			console.log($('.recv-info'));
			if(state == 'off'){
				$('.recv-info')[0].disabled=true;
				$('.recv-info')[1].disabled=true;
			}
			else
			{
				$('.recv-info')[0].disabled=false;
				$('.recv-info')[1].disabled=false;
			}
		
	}
</script>
</head>
<body>
<!-- Navigation -->
	<c:import url='/WEB-INF/views/includes/navigation.jsp'>
		<c:param name="active" value="shopping" />
	</c:import>
<div class="container">	
	<div id="row">
			<div id="col-lg-9">
			<h1>주문 하기</h1>
				<form class="board-form" name="order-info" method="post" action="${pageContext.servletContext.contextPath }/order"
				enctype="multipart/form-data">
				<div class="card mt-4">
					<div class="card-body">
					<h3 style="text-align: center;">주문 내역</h3>
					<table class='table'>
							<colgroup>
								<col width="5%">
								<col width="15%">
								<col width="55%">
								<col width="10%">
								<col width="15%">
							</colgroup>
							<tr>
								<th>번호</th>
								<th>이미지</th>
								<th>상품정보</th>
								<th>수량</th>
								<th>가격</th>
							</tr>
							<c:forEach items='${cartList }' var='vo' varStatus="status">
								<tr id="${vo.seq_no }">
									<td>${status.count }
										<input type="hidden" name="seq_no" value="${vo.seq_no }">
										<input type="hidden" name="orderList[${status.count-1 }].product_no" value="${vo.product_no }">
										<input type="hidden" name="orderList[${status.count-1 }].pd_detail_no" value="${vo.pd_detail_no }">
										<c:choose>
										<c:when test="${not empty cartList}">
											<input type="hidden" name="iscart" value="true">
										</c:when>
										<c:otherwise>
											<input type="hidden" name="iscart" value="false">
										</c:otherwise>
										</c:choose>
									</td>
									<td>
										<img src='${pageContext.servletContext.contextPath }${vo.image}'>
									</td>
									<td>${vo.name }<br>
										옵션 : [${vo.option }]
									</td>
									<td>${vo.qty }
										<input type="hidden" name="orderList[${status.count-1 }].qty" value="${vo.qty }">
									</td>
									<td>${vo.price }
										<input type="hidden" name="orderList[${status.count-1 }].pay" value="${vo.price }">
									</td>
								</tr>
							</c:forEach>
						</table>
							<input type="hidden" value="${pay }" name="pay">
						</div>
					</div>
					<hr>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">주문 정보</th>
						</tr>
						<tr>
							<td class="label">주문자 이름</td>
							<td><input type="text" name="ord_name" value=""></td>
						</tr>
						<tr>
							<td class="label">주문자 연락처</td>
						<td><input type="tel" name="ord_tel"></td>
						</tr>
						<tr>
			      			<td class="label">주문자 받는자 동일</td>
			      			<td>
			      				예<input type="radio" name="is_same" value="true" onclick='recvActive("off")'>
			      				아니오<input type="radio" name="is_same" value="false" onclick='recvActive("on")' checked="checked">
			      			</td>      			
			      		</tr>   
						<tr>
							<td class="label">받는분 이름</td>
							<td>
								<input class='recv-info' type="text" name="recv_name" value="">
							</td>
						</tr>
						<tr>
							<td class="label">받는분 연락처</td>
							<td><input class='recv-info' type="tel" name="recv_tel"></td>
						</tr>
						<tr>
							<td class="label">배송 주소</td>
							<td><input type="text" name="destination" size="100"></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }">취소</a>
						<input type="submit" value="주문">
					</div>
				</form>				
			</div>
		</div>
	</div>
		<!-- Footer -->
	<c:import url='/WEB-INF/views/includes/footer.jsp' />
	<!-- /.Footer -->
</body>
</html>