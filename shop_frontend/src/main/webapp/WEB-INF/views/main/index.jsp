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
	<link href="${pageContext.servletContext.contextPath }/assets/css/shop-hom
	epage.css" rel="stylesheet">
	<link href="${pageContext.servletContext.contextPath }/assets/js/category.js" rel="script">
	<script>
		function showProductBySubCategory(main_no, sub_no) {
		$.ajax({
					url : "${pageContext.servletContext.contextPath }/category/"
							+ main_no + "/" + sub_no,
					type : "get",
					cache : false,
					dataType : "json",
					data : "",
					success : function(response) {
						var productList = response.data;

						/* html 초기화*/
						$("#product_list").html("");

						/* before생성*/
						var img_before = '<div class="col-lg-4 col-md-6 mb-4">'
								+ '<div class="card h-100">';

						var img_after = '</a>' + '<div class="card-body">'
								+ '<h4 class="card-title">';

						var after = '</div>'
								+ '<div class="card-footer">'
								+ '<small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>'
								+ '</div>' + '</div>' + '</div>';
						/* 상품조회*/
						for ( var i in productList) {
							var vo = productList[i];
							var tag = img_before
									+ '<a href="${pageContext.servletContext.contextPath }/product/'+vo.product_no+'">'
									+ '<img class="card-img-top" src="${pageContext.servletContext.contextPath }/assets/image/'
								+ vo.image + '" alt="">'
									+ img_after + '<a href="${pageContext.servletContext.contextPath }/product/'+vo.product_no+'">' + vo.name
									+ '</a>' + '</h4>' + '<h5>' + vo.price
									+ '</h5>' + '<p class="card-text">'
									+ vo.summary_desc + '</p>' + after;

							$("#product_list").append(tag);

						}
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
	<div class="container">
		<div class="row">
			<!-- 카테고리 include -->
			<c:import url="/WEB-INF/views/includes/category.jsp"></c:import>
			<!-- /.col-lg-3 -->

			<div class="col-lg-9">
				<div id="carouselExampleIndicators" class="carousel slide my-4"
					data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#carouselExampleIndicators" data-slide-to="0"
							class="active"></li>
						<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
						<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
					</ol>
					<div class="carousel-inner" role="listbox">
						<div class="carousel-item active">
							<img class="d-block img-fluid" src="http://placehold.it/900x350"
								alt="First slide">
						</div>
						<div class="carousel-item">
							<img class="d-block img-fluid" src="http://placehold.it/900x350"
								alt="Second slide">
						</div>
						<div class="carousel-item">
							<img class="d-block img-fluid" src="http://placehold.it/900x350"
								alt="Third slide">
						</div>
					</div>
					<a class="carousel-control-prev" href="#carouselExampleIndicators"
						role="button" data-slide="prev"> <span
						class="carousel-control-prev-icon" aria-hidden="true"></span> <span
						class="sr-only">Previous</span>
					</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
						role="button" data-slide="next"> <span
						class="carousel-control-next-icon" aria-hidden="true"></span> <span
						class="sr-only">Next</span>
					</a>
				</div>
				<div id="sub_category_list" style="margin-bottom: 10px">
					<c:forEach items='${subCategoryList }' var='vo'>
						<a href="#" onclick="showProductBySubCategory(${vo.main_no },${vo.sub_no })">${vo.name } &nbsp</a>
					</c:forEach>
				</div>
				<div class="row" id="product_list">
				<c:choose>
				<c:when test="${not empty productList}">
				<c:forEach items='${productList }' var='vo'>
					<div class="col-lg-4 col-md-6 mb-4">
						<div class="card h-100">
							<c:set var="productDetailPage" value="${pageContext.servletContext.contextPath }/product/${vo.product_no }" />
							<a href="${productDetailPage }">
							<img class="card-img-top"
								src="${pageContext.servletContext.contextPath }/assets/image/${vo.image }" alt=""></a>
							<div class="card-body">
								<h4 class="card-title">
									<a href="${productDetailPage }">${vo.name }</a>
								</h4>
								<h5>${vo.price }</h5>
								<p class="card-text">${vo.summary_desc }</p>
							</div>
							<div class="card-footer">
								<small class="text-muted">&#9733; &#9733; &#9733;
									&#9733; &#9734;</small>
							</div>
						</div>
					</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<h1>등록된 상품이 존재하지 않습니다.</h1>
				</c:otherwise>
				</c:choose>
				</div>
			
			</div>
			
			
		</div>
		
	</div>

	<!-- Footer -->
	<c:import url='/WEB-INF/views/includes/footer.jsp' />
	<!-- /.Footer -->
</body>

</html>
