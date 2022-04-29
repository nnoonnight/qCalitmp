


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	
	<img src="<c:url value='/resources/static/images/home.jpg'/>">
	<a href="<c:url value='/member/login'/>"><button>로그인</button></a>
	<a href="<c:url value='/member/insert'/>"><button>회원가입</button></a>
</body>
</html>
