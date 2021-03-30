<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../module/header.jsp" %>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
<%--로그인 성공 --%>
<c:if test="${check==1}">
	<c:set var="memId" value="${id}" scope="session"/>
	<meta http-equiv="Refresh" content="0;url=main.do">	
</c:if>

<!-- 로그인 실패 -->
<c:if test="${check==0}">
	<script>	
	alert("암호가 틀립니다.");
	history.back();
	</script>
</c:if>
<c:if test="${check==-1}">
	<script>
	alert("존재하지 않는 아이디입니다.");
	history.back();
	</script>
</c:if>



