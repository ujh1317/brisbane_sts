<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateForm.jsp</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <h2>글수정</h2>
  <form method="post" action="faqUpdatePro.do">
    <table border="1" align="center">
    
    <tr>
      <td bgcolor="#ecf7fd">글쓴이</td>
      <td bgcolor="f9fafb">
       <input type="text" name="name" value="${faqDto.name}"size="30">
       <input type="hidden" name="num" value="${faqDto.num}">
      </td>
    </tr>
    
    <tr>
      <td bgcolor="#ecf7fd">글 제목</td>
      <td bgcolor="f9fafb"><input type="text" name="subject" value="${faqDto.subject}" size="40"></td>
    </tr>
    
    <tr>
     <td bgcolor="#ecf7fd">글 내용</td>
     <td bgcolor="f9fafb"><textarea name="content" rows="10" cols="50">${faqDto.content}</textarea></td>
    </tr>
    
    <tr>
      <td>암호</td>
      <td><input type="password" name="pass" size="20"></td>
    </tr>
    
    <tr>
      <td colspan="2" align="center">
       <input type="submit" value="글수정">
       <input type="reset" value="다시쓰기">
       <input type="button" value="글목록" onClick="location.href='faqList.do?pageNum=${pageNum}'">
      </td>
    
    </tr>
    
    
    
    </table>
  </form>


</body>
</html>