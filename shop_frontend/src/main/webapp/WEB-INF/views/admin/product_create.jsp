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
	<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
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
<script>
var i = 0;
	function getSubCategory(main_no){
		 $.ajax({	
				url : "${pageContext.servletContext.contextPath }/admin/category/list/"+main_no,
				type : "get",
				cache : false,
				success : function(data) {
					console.log(data);
					$('#sub_no').html("<option value='' selected>-- 선택 --</option>")
					for(var i=0; i<data.length; i++){
						var element = "<option value='"+data[i].sub_no+"' >"+data[i].name+"</option>";
						$('#sub_no').append(element);
					}
				},
				error :function(data){
				    alert("error");
				  }
			});
	};
	
	function addOption(){
		var element ='<div id="op'+i+'">'+
			'사이즈 <input class="option-style" type="text" name="option1">'+
		' 컬러 <input class="option-style" type="text" name="option2">'+
		' <button class="option-style" type="button" onclick="delOption('+i+')">빼기</button>'+
		'</div>';
		
		$('#option').append(element);
		i++;
	};
	
	
	function delOption(i){
		$('#op'+i).remove();
		i--;
	};
	
	function createOptionList(){
		var param = $("form[name=product-info]").serialize();
		$.ajax({
			url : "${pageContext.servletContext.contextPath }/option/list",
			type : "get",
			dataType : "json",
			data : param,
			success : function(list) {
			$('#option-list')[0].hidden = false;
			for(var i=0; i<list.length; i++){
				var element =
				'<div id="oplist'+i+'">'+
				'옵션 <input type="text" readonly="readonly" name="options" value="'+list[i]+'" />'+
					' 재고 <input type="number" name="inventory" min="1"/>'+
				'</div>';
				$('#option-list-td').append(element); 
				}
				
			},
			error :function(data){
			    alert("error");
			  }
		});
	}; 
</script>
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

			<div id="content">
			<div id="product">
				<form class="board-form" name="product-info" method="post" action="${pageContext.servletContext.contextPath }/admin/product/registration"
				enctype="multipart/form-data">
					<h1>상품 등록</h1>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">상품 등록</th>
						</tr>
						<tr>
							<td class="label">상품명</td>
							<td><input type="text" name="name" value=""></td>
						</tr>
						<tr>
							<td class="label">가격</td>
						<td><input type="number" name="price" min="1"></td>
						</tr>
						<tr>
			      			<td class="label">이미지</td>
			      			<td><input type="file" name="upload-image"></td>      			
			      		</tr>   
						<tr>
							<td class="label">진열여부</td>
							<td><input type="radio" name="display" value="true" checked="checked"/> 예
								<input type="radio" name="display" value="false"/> 아니오
							</td>
						</tr>
						<tr>
							<td class="label">요약설명</td>
							<td><input type="text" name="summary_desc" size="100"></td>
						</tr>
						<tr>
							<td class="label">상세설명</td>
							<td><textarea rows="5" cols="100" name="detail_desc"></textarea></td>
						</tr>
						<tr>
							<td class="label">제조일자</td>
							<td><input type="date" name="manufacturing_date"></td>
						</tr>
						<tr>
							<td class="label">카테고리</td>	
							<td><select id="main_no" name='main_no' onchange="getSubCategory(this.value)">
						  <option value='' selected>-- 선택 --</option>
						  <c:forEach items='${categoryList }' var='vo'>
						  	<option value='${vo.main_no }'>${vo.name }</option>
						  </c:forEach>
						  </select>
						<select id='sub_no' name='sub_no'>
						  <option value='' selected>-- 선택 --</option>
						</select></td>
						</tr>
						<tr>
							<td class="label">옵션</td>
							<td id='option'>사이즈 
							<input class="option-style" type="text" name="option1">
								컬러 <input class="option-style" type="text" name="option2">
								<button class="option-style" type="button" onclick="addOption()">추가</button>
								<button id="option-create-list" type="button" onclick="createOptionList()">옵션리스트 생성</button>
							</td>
							
						</tr>
						<tr id="option-list" hidden="true">
							<td class="label">옵션리스트</td>
							<td id="option-list-td"></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/admin/product">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
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
