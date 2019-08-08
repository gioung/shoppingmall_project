<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!------ Include the above in your HEAD tag ---------->

<!Doctype html>
<html>
<head>
     <meta charset="UTF-8">
     <title>Registration Form</title>
     <meta name="viewport" content="width=device-width, initial-scale=1">
     <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
     <link href="${pageContext.servletContext.contextPath }/assets/css/user-join.css" rel="stylesheet">
	 <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	 <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	 
</head>
<body>
 <div class="container">
 <!---heading---->
     <header class="heading"> 회원 가입</header><hr></hr>
	<!---Form starting----> 
	<form action="${pageContext.servletContext.contextPath }/user/join" method="post">
	<div class="row ">
	<!-----For email---->
		 <div class="col-sm-12">
		     <div class="row">
			     <div class="col-xs-4">
		             <label class="mail" >이메일</label></div>
			     <div class="col-xs-8"	>	 
			          <input type="email" name="email"  id="email" placeholder="이메일을 입력하세요." class="form-control" >
		         </div>
		     </div>
		 </div>
	 <!-----For Password and confirm password---->
          <div class="col-sm-12">
		         <div class="row">
				     <div class="col-xs-4">
		 	              <label class="pass">패스워드</label></div>
				  <div class="col-xs-8">
			             <input type="password" name="password" id="password" placeholder="패스워드를 입력하세요." class="form-control">
				 </div>
          </div>
		  </div>
		  
		  <div class="col-sm-12">
             <div class="row">
			     <div class="col-xs-4">
          	         <label class="firstname">이름</label> </div>
		         <div class="col-xs-8">
		             <input type="text" name="name" id="name" placeholder="이름을 입력하세요." class="form-control ">
             </div>
		      </div>
		 </div>
		 
		 <div class="col-sm-12">
		     <div class="row">
			     <div class="col-xs-4">
                     <label class="lastname">연락처</label></div>
				<div class ="col-xs-8">	 
		             <input type="tel" name="phone_num" id="phone_num" class="form-control last" placeholder="연락처를 입력하세요.">
                </div>
		     </div>
		 </div>
		 
         <div class="col-sm-12">
		     <div class="row">
			     <div class="col-xs-4">
                     <label class="lastname">생일</label></div>
				<div class ="col-xs-8">	 
		             <input type="date" name="birth" id="birth" class="form-control last">
                </div>
		     </div>
		 </div>
		  
     <!-----------For Gender-------->
         <div class="col-sm-12">
		     <div class ="row">
                 <div class="col-xs-4 ">
			       <label class="gender">Gender:</label>
				 </div>
			 
			     <div class="col-xs-4 male">	 
				     <input type="radio" name="gender"  id="gender" value="boy">Male
				 </div>
				 
				 <div class="col-xs-4 female">
				     <input type="radio"  name="gender" id="gender" value="girl" >Female
			     </div>
			
		  	 </div>
		     <div class="col-sm-12">
		         <input type="submit" value="가입하기">
		   </div>
		 </div>
	 </div>	 
	</form>	 		 
		 
</div>

</body>		
</html>
	 
	 