<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="resources/qna/style.css" rel="stylesheet" type="text/css">
<script>
	function confirmDelete(){
		var c=confirm('게시물을 삭제 하려면 확인을 눌러주세요.');
		if(c==true){
			location='qnaDelete.do?num=${num}&pageNum=${pageNum}';
		}else{
			return false;
		}
	
	}
</script>
</head>
<body>

	<table align="center" width="700">
		<tr><td colspan="4" align="right"><br><font size="7"><b>Q&A</b></font></td></tr>
		
		<tr>
			<td width="70" bgcolor="#c8e5f9">글제목</td>		
			<td width="250" bgcolor="#e1f1fc">${qnaDto.subject}</td>		
			<td width="70" bgcolor="#c8e5f9">작성일</td>		
			<td bgcolor="#e1f1fc">
		
			<c:if test="${qnaDto.regdate eq qnaDto.modifydate}">
			${qnaDto.regdate}
			</c:if>
			
			<c:if test="${qnaDto.regdate ne qnaDto.modifydate}">
			[수정일]${qnaDto.modifydate}
			</c:if>
			
			</td>		
		</tr>
		
	</table>

	<table align="center" width="700">
		
		<tr>
			<td width="70" bgcolor="#c8e5f9">작성자</td>		
			<td width="250" bgcolor="#e1f1fc">${qnaDto.writer}</td>		
			<td width="70" bgcolor="#c8e5f9">카테고리</td>		
			<td bgcolor="#e1f1fc">${qnaDto.category}</td>		
			<td width="70" bgcolor="#c8e5f9">조회수</td>		
			<td bgcolor="#e1f1fc">${qnaDto.readcount}</td>		
		</tr>
		
		<tr height="200">
			<td colspan="6" bgcolor="#ecf7fd">${content}</td>
		</tr>
		
	</table>	
	
	<table align="center" width="700">
		
		<tr>
			<td align="left">
				<input type="button" value="리스트" onClick="location='qnaList.do?pageNum=${pageNum}'">
				<input type="button" value="글쓰기" onClick="location='qnaWriteForm.do'">
			</td>
		
			<td align="right">
			
			<c:if test="${sessionScope.memId eq qnaDto.writer}">
				<input type="button" value="글수정" onClick="location='qnaUpdateForm.do?num=${num}&pageNum=${pageNum}'">
				<input type="button" value="글삭제" onClick="confirmDelete()">
			</c:if>
			<c:if test="${sessionScope.memId eq qnaDto.writer or sessionScope.memId eq 'admin'}">
				<input type="button" value="답글쓰기" onClick="location='qnaWriteForm.do?num=${num}&pageNum=${pageNum}&ref=${ref}&re_step=${re_step}&re_level=${re_level}'">
			</c:if>
			</td>
		</tr>
		<br><br>
	</table>	
</body>
</html>
