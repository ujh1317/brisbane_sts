<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeUpdateForm</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
<script>
function writeCheck(){
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
   
	return true;
 }//writeCheck
</script>
</head>
<body>
	<h2>update</h2>
	<form name="writeForm" method="post" action="${ctxpath}/noticeUpdatePro.do" onsubmit="return writeCheck()">
		<table width="700">
			<tr>
				<td bgcolor="#ecf7fd">글쓴이</td>
				<td bgcolor="f9fafb">
					&nbsp;${noticeDto.writer}
					<input type="hidden" name="num" value="${noticeDto.num}">
				</td>
			</tr>
			<tr>
				<td bgcolor="#ecf7fd">글제목</td>
				<td bgcolor="f9fafb"><input type="text" name="subject" id="subject" size="60" value="${noticeDto.subject}"></td>
			</tr>
			<tr>
				<td bgcolor="#ecf7fd">글내용</td>
				<td bgcolor="f9fafb"><textarea name="content" rows="10" cols="80">${noticeDto.content}</textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="글수정">
					<input type="reset" value="다시쓰기">
					<input type="button" value="글목록" onclick="location.href='noticeList.do?pageNum=${pageNum}'">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>