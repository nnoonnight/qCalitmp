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



	<form:form commandName="boardData">
		<table border="1">
			<tr>
				<td>제목</td>
				<td><form:input path="boardTitle" placeholder="8자리 숫자 입력" /> 
				<form:errors path="boardTitle" /></td>

			</tr>
			<tr>
				<td>내용</td>
				<td><form:textarea path="boardContent" /> 
				<form:errors path="boardContent" /></td>

			</tr>
		</table>

		<input type="hidden" name="questionSeq" value="${questionSeq}" />
		<input type="submit" value="글쓰기" />
	</form:form>

</body>
</html>