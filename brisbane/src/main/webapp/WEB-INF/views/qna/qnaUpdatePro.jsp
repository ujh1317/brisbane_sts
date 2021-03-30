<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<link href="resources/qna/style.css" rel="stylesheet" type="text/css">

<c:if test="${check==1}">
<meta http-equiv="refresh" content="0;url=qnaList.do?pageNum=${pageNum}">
</c:if>

<c:if test="${check==0}">
<a href="javascript:history.back()">[이전으로 돌아가기]</a>
</c:if>