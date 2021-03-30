<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>content.jsp</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <h2>글 내용입니다.</h2>
  <table border="1">
    
    <tr>
      <td bgcolor="#c8e5f9">글 번호</td>
      <td bgcolor="#e1f1fc">${faqDto.num}</td>
      <td bgcolor="#c8e5f9">조회수</td>
      <td bgcolor="#e1f1fc">${faqDto.count}</td>
    </tr>
    
    <tr>
      <td bgcolor="#c8e5f9">작성자</td>
      <td bgcolor="#e1f1fc">${faqDto.name}</td>
      <td bgcolor="#c8e5f9">작성일</td>
      <td bgcolor="#e1f1fc">
      <fmt:formatDate value="${faqDto.regdate}" pattern="yyyy-MM-dd"/>
      </td>
    </tr>
    
    <tr>
      <td bgcolor="#c8e5f9">글 제목</td>
      <td bgcolor="#e1f1fc" colspan="3">${faqDto.subject}</td>
    </tr>
    
    <tr>
      <td bgcolor="#c8e5f9">글 내용</td>
      <td bgcolor="#e1f1fc" colspan="3"><pre>${faqDto.content}</pre></td>
    </tr>
    
    <tr>
      <td colspan="4" align="center">
      <input type="button" value="글 수정" onClick="location.href='faqUpdate.do?num=${num}&pageNum=${pageNum}'">
      <input type="button" value="글 삭제" onClick="location.href='faqDelete.do?num=${num}&pageNum=${pageNum}'">
      <input type="button" value="글 쓰기" onClick="location.href='faqWriteForm.do'">
      <input type="button" value="답글쓰기" onClick="location.href='faqWriteForm.do?num=${num}&ref=${faqDto.ref}&re_step=${faqDto.re_step}&re_level=${faqDto.re_level}'">
      <input type="button" value="글 목록" onClick="location.href='faqList.do?num=${num}&pageNum=${pageNum}'">
      </td>
    </tr>
  </table>
</body>
</html>