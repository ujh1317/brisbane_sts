<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<c:if test="${check==1}">
	<c:remove var="memId" scope="session"/><!-- 회원 탈퇴하면서 아이디 세션 무효화 -->
	<c:remove var="name" scope="session"/><!-- 회원 탈퇴하면서 리플용 name 세션 무효화 -->
	<table width="270" cellpadding="5">
		<tr height="40">
			<td align="center"><font size="+1"><b>회원정보가 삭제되었습니다.</b></font></td>
		</tr>
		<tr height="40">
			<td align="center">
				<p>안녕히가세요.</p>
				<form>
				<input type="button" value="메인으로 " onClick="location.href='main.do'">
				</form>
			</td>
		</tr>
	</table>
	
	<meta http-equiv="Refresh" content="5;url=main.do">
</c:if>

<c:if test="${check==-1}">
	<script>
		alert("암호 틀림");
	</script>
	<meta http-equiv="Refresh" content="0;url=memberDeleteForm.do">
</c:if>

</body>
</html>