<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${!empty memberLogin}">
		<h2>로그인 성공</h2>
		<table border="1">
			<tr>
				<th>회원 번호</th>
				<th>회원 ID</th>
				<th>회원 닉네임</th>
				<th>회원 생일</th>
				<th>회원 가입 날짜</th>
				<th>회원 인증 여부</th>
				<th>회원 레벨</th>
			</tr>
			<tr>
				<td>${memberLogin.memberSeq}</td>
				<td>${memberLogin.memberId}</td>
				<td>${memberLogin.memberNickname}</td>
				<td>${memberLogin.memberBirthDay}</td>
				<td>${memberLogin.memberRegDay}</td>
				<td>${memberLogin.memberAuth}</td>
				<td>${memberLogin.memberLevel}</td>


			</tr>
		</table>
		<a href="<c:url value='/member/logout'/>"><button>로그아웃</button></a>
	</c:if>
	<form:form commandName="boardEditData">
		<table border="1">
			<tr>
				<td><form:label path="boardTitle">제목</form:label></td>
				
				<td><form:input path="boardTitle"/> 
				<form:errors path="boardTitle" /></td>

			</tr>
			<tr>
				<td>내용</td>
				<td><form:input path="boardContent" label="${ boardContent}"/> 
				<form:errors path="boardContent" /></td>

			</tr>
		</table>

		
		<input type="submit" value="글쓰기" />
	</form:form>

</body>
</html>