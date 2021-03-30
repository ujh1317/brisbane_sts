<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
<c:remove var="memId" scope="session"/><!-- 로그아웃하면서 아이디 세션 무효화 -->
<c:remove var="name" scope="session"/><!-- 로그아웃하면서 리플용 name 세션 무효화 -->
<meta http-equiv="Refresh" content="0;url=main.do"/>

