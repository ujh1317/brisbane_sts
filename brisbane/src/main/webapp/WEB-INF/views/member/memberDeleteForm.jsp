<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function begin(){
	document.myForm.pw.focus();
}

function check(){
	if(!document.myform.pw.value){
		alert("암호를 입력하세요.");
		document.myForm.pw.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body onLoad="begin()">
<form name="myForm" method="post" action="memberDeletePro.do" onSubmit="return check()">
<table width="260" border="1">
	<tr>
		<td colspan="2" valign="middle" align="center" bgcolor="#ecf7fd">
			<font size="+1"><b>회원 탈퇴</b></font>
		</td>
	</tr>
	<tr height="30">
		<td bgcolor="#ecf7fd">암호</td>
		<td bgcolor="#f9fafb">
			<input type="password" name="pw" id="pw" size="15">
			<input type="hidden" name="id" id="id" value="${sessionScope.memId}">			
		</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center" bgcolor="#ecf7fd">
			<input type="submit" value="회원탈퇴">
			<input type="button" value="취소" onClick="location.href='main.do'">
		</td>
	</tr>
	
</table>

</form>
</body>
</html>
