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

<table>
	<tr>
		<td>
			<form name="modifyForm" method="post" action="memberModifyForm.do">
				<input type="hidden" name="id" id="id" value="${sessionScope.memId}">
				<input type="submit" value="내 정보 수정">
			</form>
		</td>
		<td>
			<form name="deleteForm" method="post" action="memberDeleteForm.do">
				<input type="hidden" name="id" id="id" value="${sessionScope.memId}">
				<input type="submit" value="회원탈퇴">
			</form>
		
		</td>
	</tr>
</table>

</body>
</html>