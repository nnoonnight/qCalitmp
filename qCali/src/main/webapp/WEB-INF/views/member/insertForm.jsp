<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
	<script type="text/javascript">
var idck = 0;
var pwck = 0;
var nickck = 0;

	function pwCheck() {

	    if(document.getElementById('memberPassword').value!='' && document.getElementById('memberPasswordCheck').value!='') {
	        if(document.getElementById('memberPassword').value==document.getElementById('memberPasswordCheck').value) {
	            document.getElementById('pwSame').innerHTML='비밀번호가 일치합니다.';
	            document.getElementById('pwSame').style.color='blue';
	            pwck = 1;
	        }
	        else {
	            document.getElementById('pwSame').innerHTML='비밀번호가 일치하지 않습니다.';
	            document.getElementById('pwSame').style.color='red';
	            
	        }
	    }
	}


		function idCheck() {
			var str = document.getElementById('memberId').value;
			var pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
		  	  if(!pattern.test(str)) {
			  		document.getElementById('idSame').innerHTML='형식에 맞게 입력하세요.';
			  	      document.getElementById('idSame').style.color='red';
			  	  } else{
			  		  document.getElementById('idSame').innerHTML='';
						$.ajax({
				            url : "/exam/member/idDup",
				            type : "POST",
				            dataType :"JSON",
				            data : {"memberId" : $("#memberId").val()},
				            success : function (data) {
				                if(data == 1) {
				                	document.getElementById('idSame').innerHTML='사용할 수 없는 이메일 입니다.';
							        document.getElementById('idSame').style.color='red';
				                } else if (data == 0) {		           
						            document.getElementById('idSame').innerHTML='사용 가능한 이메일 입니다.';
						            document.getElementById('idSame').style.color='blue';
						            idck = 1;
				                }
				            }

				        })
			  	  }    	
			

		   
		}

	    
	  
	  function nickCheck() {
	        $.ajax({
	            url : "/exam/member/nicknameDup",
	            type : "POST",
	            dataType :"JSON",
	            data : {"memberNickname" : $("#memberNickname").val()},
	            success : function (data) {
	                if(data == 1) {
	                	document.getElementById('nickSame').innerHTML='사용할 수 없는 닉네임 입니다.';
				        document.getElementById('nickSame').style.color='red';
	                } else if (data == 0) {
	                    document.getElementById('nickSame').innerHTML='사용 가능한 닉네임 입니다.';
			            document.getElementById('nickSame').style.color='blue';
			            nickck = 1;
	                }
	            }

	        })
	    }

	  function dateCheck(){
	  var str = document.getElementById('memberBirthDay').value;
	  var pattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
	  if(!pattern.test(str)) {
		document.getElementById('date').innerHTML='형식에 맞게 입력하세요.';
	      document.getElementById('date').style.color='red';
	  } else{
		  document.getElementById('date').innerHTML='';
	  }
	  }
	  
	  
	  function DoSignUp() {

			 if (idck == 0) {alert("이메일 중복 체크를 해주세요"); $("#memberId").focus(); return; }
			 if (pwck == 0) {alert("비밀번호 확인 체크를 해주세요"); $("#memberPassword").focus(); return; }
			 if (nickck == 0) {alert("닉네임 중복 체크를 해주세요"); $("#memberNickname").focus(); return; }
		  
		  if (confirm("회원가입을 하시겠습니까?")) {
			  location.href = "<c:url value='/member/insert'/>";
		  }
	  }
	</script>
<body>


회원가입 양식
<form:form commandName="InsertCommand"  id="insertForm" >

<table border="1">
		<tr>
			<th>이메일</th><td><form:input path="memberId" id="memberId" placeholder="이메일 입력  "/><form:errors path="memberId"/>
				<button type="button" onclick="idCheck()">아이디 중복확인</button>
				<span id="idSame"></span>
				</td>
		</tr>
		<tr>
			<th>비밀번호</th><td><form:password path="memberPassword" id="memberPassword"/><form:errors path="memberPassword"/></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th><td><form:password path="memberPasswordCheck" id="memberPasswordCheck"/><form:errors path="memberPasswordCheck"/>
			<button type="button" onclick="pwCheck()"> 비밀번호 확인 </button>
			<span id="pwSame"></span>
		</td>
		</tr>
		<tr>
			<th>닉네임</th><td><form:input path="memberNickname" id="memberNickname"/><form:errors path="memberNickname"/>
			<button type="button" onclick="nickCheck()"> 닉네임 중복확인 </button>
			<span id="nickSame"></span>
			</td>
		</tr>
		<tr>
			<th>생년월일</th><td><form:input path="memberBirthDay" id="memberBirthDay" placeholder="yyyy-mm-dd" onchange="dateCheck()"/><form:errors path="memberBirthDay"/>
			
			<span id="date"></span>
			</td>
		</tr>
		
	</table>
	<br>
	
	<input type="button" class="btn btn-lg btn-success btn-block" value="회원가입" onclick="DoSignUp();" />

</form:form>

</body>
</html>