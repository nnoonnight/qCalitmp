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



	<form:form commandName="diaryData">
		<table border="1">
			<tr>
				<td>제목</td>
				<td><form:input path="diaryTitle" placeholder="8자리 숫자 입력" /> 
				<form:errors path="diaryTitle" /></td>

			</tr>
			<tr>
				<td>내용</td>
				<td><form:textarea path="diaryContent" /> 
				<form:errors path="diaryContent" /></td>

			</tr>
			<tr>
				<td>공개여부</td>
				<td>
					<input type="radio" name="open" value="T" checked/>공개 
					<input type="radio" name="open" value="F" />비공개				
				</td>

			</tr>
			
		</table>

		
		<input type="hidden" name="memberSeq" value="${memberLogin.memberSeq}">
		<input type="submit" value="글쓰기" />
	</form:form>

</body>
</html>