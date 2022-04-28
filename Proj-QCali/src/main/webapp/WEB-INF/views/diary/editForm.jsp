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
	<form:form commandName="DiaryUpdateCommand"  enctype="multipart/form-data">
		<table border="1" >
			<tr>
				<td>제목</td>
				<td><form:input path="diaryTitle" value="${diaryList.diaryTitle }"/> 
				<form:errors path="diaryTitle" /></td>

			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="diaryContent">${diaryList.diaryContent}</textarea> 
				<form:errors path="diaryContent" /></td>

			</tr>
			<tr>
				<td>파일 업로드</td>
				<td>
				<c:if test="${ empty diaryList.diaryImg ||  diaryList.diaryImg eq 'deleted'}">
					<input type="file" name="img">
				</c:if>
				<c:if test="${ !empty diaryList.diaryImg}">
					<img src="<c:url value='/resources/upload/${diaryList.diaryImg }'/>" width="200" >
					<input type="file" name="img">
				</c:if>

				</td>
		
			</tr>
			<tr>
				<td>공개여부</td>
				<td>
				<c:if test="${diaryList.diaryOpen == 'T' }">
					<input type="radio" name="open" value="T" checked/>공개 
					<input type="radio" name="open" value="F" />비공개	
				</c:if>	
				<c:if test="${diaryList.diaryOpen == 'F' }">
					<input type="radio" name="open" value="T" />공개 
					<input type="radio" name="open" value="F" checked/>비공개	
				</c:if>			
				</td>

			</tr>
		</table>

		
		<input type="submit" value="글쓰기" />
	</form:form>

</body>
</html>