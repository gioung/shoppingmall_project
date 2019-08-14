<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 장바구니</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.servletContext.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
	<link href="${pageContext.servletContext.contextPath }/assets/css/shop-item.css" rel="stylesheet">
<style type="text/css">
 /*  table {
    width: 100%;
    border: 1px solid #444444;
  }
  table th,table td {
  	text-align: center;
    border: 1px solid #444444;
  } */
  table td img {
  	width: 70px;
  	height: 70px;
  } 
  #order-button{
  	float: right;
  	margin-top: 10px;
  }
  #cancel-button{
  	float: left;
  	margin-top: 10px;
  }
</style>
<script>
	
	function updateQty(seq_no){
		var qty = $('#qty')[0].value;
		$.ajax({	
			url : "${pageContext.servletContext.contextPath }/cart/update/"+seq_no,
			type : "post",
			data : {"qty": qty},     
			dataType: "json",
			success : function(response) {
				console.log(response);
			},
			error :function(data){
			    alert("error");
			  }
		}); 

	
	}
	
	function deleteProduct(seq_no){
		$.ajax({	
			url : "${pageContext.servletContext.contextPath }/cart/delete/"+seq_no,
			type : "get",
			success : function(response) {
				//엘리먼트를 삭제
				$('#'+seq_no).remove();
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
	<c:if test="${param.result eq 'fail'}">
		<script>alert('상품삭제실패')</script>
	</c:if>
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<!-- 카테고리 include -->
			<c:import url="/WEB-INF/views/includes/category.jsp"/>
			<!-- /.col-lg-3 -->
			
			<div class="col-lg-9">
				<form action="" method="post" name="cartvalue">
				<div class="card mt-4">
					<div class="card-body">
					<h1>장바구니</h1>
						<table class='table'>
							<colgroup>
								<col width="5%">
								<col width="8%">
								<col width="32%">
								<col width="14%">
								<col width="5%">
								<col width="10%">
								<col width="16%">
								<col width="10%">
							</colgroup>
							<tr>
								<th>No</th>
								<th>이미지</th>
								<th>상품명</th>
								<th>옵션</th>
								<th>수량</th>
								<th>가격</th>
								<th>담은날짜</th>
								<th>선택</th>
							</tr>
							<c:forEach items='${cartList }' var='vo' varStatus="status">
								<tr id="${vo.seq_no }">
									<td>${status.count }</td>
									<td><a href='${pageContext.servletContext.contextPath }/product/${vo.product_no }'>
											<img src='${pageContext.servletContext.contextPath }${vo.image}'>
										</a>
									</td>
									<td><a href='${pageContext.servletContext.contextPath }/product/${vo.product_no }'>${vo.name }</a></td>
									<td>${vo.option }</td>
									<td><input id="qty" type="number" value='${vo.qty }' min="1" max="99" />
									<button type="button" onclick="updateQty(${vo.seq_no })" >Edit</button>
									</td>
									<td>${vo.price }
									<td>${vo.reg_date }</td>
									<td>
										<button type="button" onclick="deleteProduct(${vo.seq_no })" >삭제</button>
										<button type="button" onclick="location.href='${pageContext.servletContext.contextPath }/order/${vo.seq_no }'	">주문</button>
									</td>
								</tr>
							</c:forEach>
						</table>
						
						<div id="cancel-button">
							<button type="button" onclick="location.href='${pageContext.servletContext.contextPath }/cart/delete'">장바구니 비우기</button>
						</div>
						<div id="order-button">
							<button type="button" onclick="location.href='${pageContext.servletContext.contextPath }/order'">주문하기</button>
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