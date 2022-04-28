<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		<a href="<c:url value='/diary/write/${memberLogin.memberSeq}'/>"><button>일기쓰기</button></a>


	</c:if>

	<table border="1">
		<tr>
			<th>일기seq</th>
			<th>일기제목</th>
			<th>일기내용</th>
			<th>이미지</th>
			<th>닉네임</th>
			<th>일기 쓴 날짜</th>
			<th>일기 좋아요</th>
			<th>일기 카운트</th>
			<th>공개여부</th>
		</tr>
		<c:if test="${ empty diaryList}">
			<tr>
				<td colspan="7">게시판에 저장된 글이 없습니다.</td>
			</tr>
		</c:if>

		<c:if test="${ !empty diaryList}">

			<tr>
				<td>${diaryList.diarySeq}</td>

				<td>${diaryList.diaryTitle}</td>
				<td>${diaryList.diaryContent}</td>
				<c:if test="${!empty diaryList.diaryImg }">
				<td><img src="<c:url value='/resources/upload/${diaryList.diaryImg }'/>" width="200" ></td>
				</c:if>
				<c:if test="${empty diaryList.diaryImg }">
				<td>	</td>
				</c:if>
				<td>${diaryList.memberNickname}</td>
				<td>${diaryList.diaryRegday}</td>
				<td>${diaryList.diaryLike}</td>
				<td>${diaryList.diaryCount}</td>
				<td>${diaryList.diaryOpen}</td>
				
			</tr>

			<div style="text-align: right;">
				<a class="text-dark heart" style="text-decoration-line: none;">
					<img id="heart" src="" height="30px">
				</a>
			</div>


			<c:if test="${myArticle == true}">

				<a href="<c:url value='/diary/edit/${diaryList.diarySeq}'/>"><button>일기
						수정</button></a>


				<button onclick="delete_button()">일기 삭제</button>
				
				<button onclick="deleteImg_button()">첨부파일 삭제</button>
				

			</c:if>




		</c:if>


	</table>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script>
		$(document).ready(function() {
			var heartval = ${diaryHeart};
			if (heartval > 0) {
				console.log(heartval);
			    $("#heart").prop("src", '<c:url value="/resources"/>'+"/static/images/like2.png");
				$(".heart").prop('name', heartval)
			} else {
				console.log(heartval);
				$("#heart").prop("src", '<c:url value="/resources"/>'+"/static/images/like1.png");
				$(".heart").prop('name', heartval)
			}
			$(".heart").on("click", function() {
				var that = $(".heart");
				console.log(that.prop('name'));
				var sendData = {
					'diarySeq' : '${diarySeq}',
					'heart' : that.prop('name'),
				};
				$.ajax({
					url : '<c:url value="/diary/heart"/>',
					type : 'POST',
					data : JSON.stringify(sendData),
					contentType: 'application/json',
					success : function(data) {
						that.prop('name', data);
						console.log("success:" + that.prop('name', data));
						if (data == 1) {
							 $('#heart').prop("src",'<c:url value="/resources"/>'+"/static/images/like2.png");
						} else {
							 $('#heart').prop("src",'<c:url value="/resources"/>'+"/static/images/like1.png");
						}
					}
				});
			});
		});
	</script>

	<script type="text/javascript">
		function delete_button() {

			if (!confirm("정말 삭제하시겠습니까??")) { //아니오
				return false;			

			} else { //예

				return location.href="<c:url value='/diary/delete?diarySeq='/>"+${diaryList.diarySeq};

			}

		}
		
		function deleteImg_button() {

			if (!confirm("정말 삭제하시겠습니까??")) { //아니오
				return false;			

			} else { //예

				return location.href="<c:url value='/diary/deleteImg?diarySeq='/>"+${diaryList.diarySeq};

			}

		}
	</script>


</body>