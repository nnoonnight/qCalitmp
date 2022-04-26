<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<style>
ul {
	list-style: none;
	width: 30%;
	display: inline-block;
}

li {
	float: left;
	margin-left: 5px;
}
</style>

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
		memberLogin.memberSeq = ${memberLogin.memberSeq}
		testMemberSeq = ${testMemberSeq}
	
		<c:set var ="memberLogin.memberSeq" value="${memberLogin.memberSeq}"/>
		<c:set var ="testMemberSeq" value="${testMemberSeq}"/>
		<c:if test="${memberLogin.memberSeq == testMemberSeq}">
		<a href="<c:url value='/diary/write/${memberLogin.memberSeq}'/>"><button>일기쓰기</button></a>
		</c:if>
		


	</c:if>

	<c:if test="${empty memberLogin}">
		<a href="<c:url value='/member/login'/>"><button>로그인</button></a>

	</c:if>

	게시글 수 : ${diaryTotal }
	<table border="1">
		<tr>
			<th>일기seq</th>
			<th>일기제목</th>
			<th>닉네임</th>
			<th>일기 쓴 날짜</th>
			<th>일기 좋아요</th>
			<th>일기 카운트</th>


		</tr>

	
		<c:if test="${ empty diaryList}">
			<tr>
				<td colspan="7">게시판에 저장된 글이 없습니다.</td>
			</tr>
		</c:if>

		<c:if test="${ !empty diaryList}">
			<c:forEach var="list" items="${diaryList}">
				
				<tr>
					<td>${list.diarySeq}</td>
					<c:if test="${memberLogin.memberSeq == testMemberSeq}" >
					<td><a href="<c:url value='/diary/detail?diarySeq=${list.diarySeq}'/>">${list.diaryTitle}</a>

					</td>


					<td>${list.memberNickname}</td>
					<td>${list.diaryRegday}</td>
					<td>${list.diaryLike}</td>
					<td>${list.diaryCount}</td>
					</c:if>
					
					<c:if test="${memberLogin.memberSeq != testMemberSeq}" >
					<c:if test="${list.diaryOpen eq 'F' }" >
					<td colspan="5">비공개 글입니다.</td>
					</c:if>
					<c:if test="${list.diaryOpen eq 'T' }" >
						<td><a href="<c:url value='/diary/detail?diarySeq=${list.diarySeq}'/>">${list.diaryTitle}</a>
					</td>
					<td>${list.memberNickname}</td>
					<td>${list.diaryRegday}</td>
					<td>${list.diaryLike}</td>
					<td>${list.diaryCount}</td>
					</c:if>
					</c:if>					
				</tr>
			</c:forEach>
		</c:if>
		
		<div>
			<ul>
				<c:if test="${pageMaker.prev }">
					<li><a
						href="mylist${pageMaker.makeQuery(pageMaker.startPage - 1) }&memberSeq=${memberLogin.memberSeq}">Previous</a>
					</li>
				</c:if>

				<c:forEach var="currentPage" begin="${pageMaker.startPage }"
					end="${pageMaker.endPage }">
					<li><a
						href="mylist${pageMaker.makeQuery(currentPage) }&memberSeq=${memberLogin.memberSeq}">${currentPage }</a></li>
				</c:forEach>

				<c:if test="${pageMaker.next }">
					<li ><a
						href="listmy${pageMaker.makeQuery(pageMaker.endPage + 1) }&memberSeq=${memberLogin.memberSeq}'/>">Next</a>
					</li>
				</c:if>
			</ul>
		</div>


	</table>
</body>
</html>