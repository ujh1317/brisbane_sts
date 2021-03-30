<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeWriteForm</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
<script>
function writeCheck(){
	if(document.writeForm.writer.value==""){
	  alert("작성자를 입력하십시오.");
	  document.writeForm.writer.focus();
	  return false;
	}
	if(document.writeForm.subject.value==""){
	  alert("제목을 입력하십시오.");
	  document.writeForm.subject.focus();
	  return false;
	}
	
	if(document.writeForm.content.value==""){
	  alert("내용을 입력하십시오.");
	  document.writeForm.content.focus();
	  return false;
	}
   
	//document.writeform.submit();
	return true;
 }//writeCheck
</script>
</head>
<body>
	<h2>write</h2>
	<form name="writeForm" method="post" action="${ctxpath}/noticeWritePro.do" onsubmit="return writeCheck()">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<input type="hidden" name="num" value="${num}">
		<input type="hidden" name="ref" value="${ref}">
		<input type="hidden" name="re_step" value="${re_step}">
		<input type="hidden" name="re_level" value="${re_level}">
		<input type="hidden" id="writer" name="writer" value="${writer}">
		
		<table width="700">
			<tr>
				<td colspan="2" align="right"><a href="${ctxpath}/noticeList.do">글목록</a></td>
			</tr>
			
			<!-- 작성자 -->
			<tr>
				<td bgcolor="#ecf7fd">작성자</td>
				<td bgcolor="f9fafb">&nbsp;${writer}</td>
			</tr>
			
			<!-- 글제목 -->
			<tr>
				<td bgcolor="#ecf7fd">글제목</td>
				<td bgcolor="f9fafb">
					<c:if test="${num==0}"><!-- 원글일때 -->
						<input type="text" name="subject" id="subject" size="60">
					</c:if>
					<c:if test="${num!=0}"><!-- 답글일때 -->
						<input type="text" name="subject" id="subject" size="60" value="[re]&nbsp;">
					</c:if>
				</td>
			</tr>
			
			<!-- 글내용 -->
			<tr>
				<td bgcolor="#ecf7fd">글내용</td>
				<td bgcolor="f9fafb"><textarea name="content" rows="10" cols="80"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<c:if test="${num==0}">
						<input type="submit" value="글쓰기">
					</c:if>
					<c:if test="${num!=0}">
						<input type="submit" value="답글쓰기">
					</c:if>
					<input type="reset" value="다시작성">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>