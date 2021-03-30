<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<link href="resources/qna/style.css" rel="stylesheet" type="text/css">
<script src="resources/qna/writeForm.js" type="text/javascript"></script>
</head>
<body>

<c:if test="${empty sessionScope.memId}">
	<table align="center">
		<tr>
			<br><td><b>로그인 후 글을 수정해주세요!</b></td><br>
		</tr>
		<tr>
			<td align="center">
				<input type="button" value="로그인 화면" onClick="location='main.do'">
				<input type="button" value="글목록" onClick="location='qnaList.do'">
			</td>
		</tr>
	
	</table>
	
</c:if>
<c:if test="${!empty sessionScope.memId and sessionScope.memId eq qnaDto.writer}">
<form name="writeForm" id="writeForm" method="post" action="qnaUpdatePro.do?pageNum=${pageNum}">

	<table align="center">
		<tr><td colspan="2" align="right"><br><font size="6"><b>Q&A</b></font></td></tr>
		<tr><td colspan="2" align="right"><font size="3"><b>글 수정</b></font></td></tr>
		
		<tr><td colspan="2" bgcolor="#ecf7fd"><font size="4"><b>제목</b></font></td></tr>
		<tr>
			<td colspan="2">

					<input type="text" name="subject" id="subject" size="78" value="${qnaDto.subject}">
					<input type="hidden" name="num" id="num" value="${num}">

				<select name="category" id="category">
				
					<option selected value="${qnaDto.category}">
						${qnaDto.category}
					</option>
				
					<option value="회원정보변경">회원정보변경</option>
					<option value="게시물 변경 요청">게시물 변경 요청</option>
					<option value="페이지 오류 신고">페이지 오류 신고</option>
					<option value="기타 문의">기타 문의</option>
				</select>
				<br><br>
			</td>
		</tr>
		<tr><td colspan="2" bgcolor="#ecf7fd"><font size="4"><b>내용</b></font></td></tr>
		<tr>
			<td colspan="2">
				
			  	<textarea id="content" name="content" rows="10" cols="90">${qnaDto.content}</textarea>						  						
			  	<br><br>
		  	</td>
	  	</tr>
		<tr>
			<td colspan="2">	
			  	공개 범위 설정 :
			  	
			  	<c:if test="${qnaDto.bounds==0}">
			  		<input type="radio" name="bounds" id="bounds" value="0" checked>공개
					<input type="radio" name="bounds" id="bounds" value="1">비공개
			  	</c:if>
			  	<c:if test="${qnaDto.bounds==1}">
			  		<input type="radio" name="bounds" id="bounds" value="0">공개
					<input type="radio" name="bounds" id="bounds" value="1" checked>비공개
			  	</c:if>
			  	
				<br>
		  	</td>
		  	
	  	</tr>
		
		<tr>
			<td align="left">
				<input type="button" value="글목록" onClick="location='qnaList.do?pageNum=${pageNum}'">
			</td>
			<td align="right">
				<input type="button" value="글 수정" onClick="check()">
				<input type="reset" value="다시쓰기">
				<br>
		  	</td>
	  	</tr>

	</table>

</form>

</c:if>

</body>
</html>