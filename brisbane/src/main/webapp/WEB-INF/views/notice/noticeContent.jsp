<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeContent</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
$(function(){ 
    
    //댓글 쓰기
    $("#btnReply").click(function(){
    	var writer="${writer}";
		var content=$("#replyContent").val(); //댓글 내용
		var num="${noticeDto.num}"; //게시물 번호
		var param={"writer": writer, "content": content, "num": num};
			$.ajax({
				type: "post",
            url: "replyWrite.do",
            data: param,
            success: function(result){
                alert("댓글이 등록되었습니다.");
            }
        });
    });
});

</script>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h2>content</h2>
	<table width="700">
		<tr>
			<td bgcolor="#c8e5f9">글번호</td>
			<td bgcolor="#e1f1fc">${noticeDto.num}</td>
			<td bgcolor="#c8e5f9">조회수</td>
			<td bgcolor="#e1f1fc">${noticeDto.readcount}</td>
		</tr>
		<tr>
			<td bgcolor="#c8e5f9">작성자</td>
			<td bgcolor="#e1f1fc">${noticeDto.writer}</td>
			<td bgcolor="#c8e5f9">작성일</td>
			<td bgcolor="#e1f1fc"><fmt:formatDate value="${noticeDto.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td bgcolor="#c8e5f9">글제목</td>
			<td bgcolor="#e1f1fc" colspan="3">${noticeDto.subject}</td>
		</tr>
		<tr>
			<td bgcolor="#c8e5f9">글내용</td>
			<td bgcolor="#e1f1fc" colspan="3"><pre>${noticeDto.content}</pre></td>
		</tr>
		<tr>
			<td bgcolor="#c8e5f9">IP</td>
			<td bgcolor="#e1f1fc" colspan="3">${noticeDto.ip}</td>
		</tr>
	</table>
	<div>
		<a href="noticeList.do?pageNum=${pageNum}">목록</a>&nbsp;&nbsp;
		<c:if test="${sessionScope.name == noticeDto.writer}">
			<a href="noticeUpdate.do?num=${num}&pageNum=${pageNum}">수정</a>&nbsp;&nbsp;
		</c:if>
		<c:if test="${sessionScope.name == noticeDto.writer || sessionScope.memId == 'admin'}">
			<a href="noticeDelete.do?num=${num}&pageNum=${pageNum}" onclick="return confirm('<c:out value="${noticeDto.num}"/>번 글을 삭제하시겠습니까?')">삭제</a>&nbsp;&nbsp;
		</c:if>
		<a href="noticeWrite.do?num=${num}&ref=${noticeDto.ref}&re_step=${noticeDto.re_step}&re_level=${noticeDto.re_level}">답글쓰기</a>&nbsp;&nbsp;
	</div>
	<br>
	<br>
	<br>
	 
	 <table>
		<tr>
			<td>
				<textarea id="replyContent" name="replyContent" rows="3" cols="90" placeholder="댓글을 입력하세요."></textarea>
			</td>
			<td><button id="btnReply">등록</button></td>
		</tr>
		</table>
		<br>
		<table width="700">
			<c:forEach var="replyList" items="${replyList}">
				<tr>
					<!-- 글번호 출력 -->
					<td width="10">
						${replyList.no}
					</td>
					
					<!-- 내용 출력 -->
					<td>&nbsp;&nbsp;${replyList.content}</td>
					
					<!-- 작성자 출력 -->
					<td width="50">${replyList.writer}</td>
					
					<!-- 작성일 출력 -->
					<td width="150"><fmt:formatDate value="${replyList.regdate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<c:if test="${sessionScope.name eq replyList.writer or sessionScope.name eq '관리자'}">
						<td width="10">
						<input type="button" value="삭제" onclick="location.href='replyDelete.do?no=${replyList.no}'">
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
</body>
</html>