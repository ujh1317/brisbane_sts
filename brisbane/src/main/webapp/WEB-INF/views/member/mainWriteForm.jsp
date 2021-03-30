<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
   <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
   <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
   <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

   <link href="resources/summernote-0.8.18-dist/summernote.css" rel="stylesheet">
   <script src="resources/summernote-0.8.18-dist/summernote.js"></script>
   
   <script src="resources/member/script.js" type="text/javascript"></script>
	<link href="resources/member/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<c:if test="${sessionScope.memId ne 'admin'}">
	<table align="center">
		<tr>
			<br><td><b>접근 권한이 없습니다.</b></td><br>
		</tr>
		<tr>
			<td align="center">
				<input type="button" value="메인으로" onClick="location='main.do'">
			</td>
		</tr>
	
	</table>
</c:if>
<c:if test="${sessionScope.memId eq 'admin'}">	
	<c:if test="${menu eq '1'}">
		<br><h4>워킹홀리데이란?</h4><br>
	</c:if>
	<c:if test="${menu eq '2'}">
		<br><h4>안전 정보</h4><br>
	</c:if>
	<c:if test="${menu eq '3'}">
		<br><h4>지역 정보</h4><br>
	</c:if>
	<c:if test="${menu eq '4'}">
		<br><h4>취업 정보</h4><br>
	</c:if>
	<form name="mainWriteForm" id="mainWriteForm" method="post" action="mainWritePro.do" onSubmit="return mainCheck()">
		<input type="hidden" name="menu" value="${menu}">
		<table align="center">
			<tr>
				<td colspan="2" align="center">
					<c:if test="${empty mainDto.data}">
						<textarea name="data" id="summernote"></textarea>
					</c:if>	
					<c:if test="${!empty mainDto.data}">
						<textarea name="data" id="summernote">${mainDto.data}</textarea>
					</c:if>	
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="button" value="메인으로" onClick="location='main.do?menu=${menu}'">
				</td>
				<td align="right">
					<input type="submit" value="글등록">
				</td>
			</tr>
		</table>
	</form>
</c:if>


</body>
</html>



