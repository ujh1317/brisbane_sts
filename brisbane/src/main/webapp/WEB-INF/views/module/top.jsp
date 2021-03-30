<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./header.jsp" %>



<c:if test="${empty sessionScope.memId}">
<input type="button" value="로그인" onClick="location='main.do'">
</c:if>

<c:if test="${!empty sessionScope.memId}">
<input type="button" value="로그아웃" onClick="location.href='memberLogout.do'">
<input type="button" value="회원정보변경" onClick="location.href='memberModify.do'">
</c:if>

<input type="button" value="회원가입" onClick="location='memberInputForm.do'">






