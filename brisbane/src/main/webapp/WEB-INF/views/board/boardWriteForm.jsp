<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>writeForm.jsp</title>

<link href="resources/member/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function check(){
   if(document.boaWriteForm.writer.value==''){
      alert("글쓴이를 입력 하시오");
      document.boardwriteForm.writer.focus();
      return false;
   }
   if(document.boardwriteForm.subject.value==''){
      alert("글제목를 입력 하시오");
      document.boardwriteForm.subject.focus();
      return false;
   }
   if(document.boardwriteForm.content.value==''){
      alert("글내용를 입력 하시오");
      document.boardwriteForm.content.focus();
      return false;
   }
   if(document.boardwriteForm.pw.value==''){
      alert("암호를 입력 하시오");
      document.boardwriteForm.pw.focus();
      return false;
   }
   return true;
}

</script>

</head>
<body>
 <h2>게시판 글쓰기</h2>
 <form method="post" name="boaWriteForm" action="WritePro.do" onSubmit="return check()">
   <input type="hidden" name="pageNum" value="${pageNum}">
   <input type="hidden" name="num" value="${num}">
   <input type="hidden" name="ref" value="${ref}">
   <input type="hidden" name="re_step" value="${re_step}">
   <input type="hidden" name="re_level" value="${re_level}">
   
   <table border="1">
   
    <tr>
     <td colspan="2" align="right">
       <a href="boardList.do">글목록</a>
     </td>
    </tr>
    
    <tr>
     <td bgcolor="#ecf7fd">글쓴이</td>
     <td bgcolor="f9fafb"><input type="text" name="writer" id="writer" size="30"></td>
    </tr>
    
    <tr>
     <td bgcolor="#ecf7fd">글제목</td>
     <td bgcolor="f9fafb">
     <!-- 원글 -->
     <c:if test="${num==0}">
       <input type="text" name="subject" id="subject" size="40">
     </c:if>
     
     <!-- 답글 -->
     <c:if test="${num!=0}">
       <input type="text" name="subject" id="subject" size="40" value="[답변]">
     </c:if>
     </td>
    </tr>
    
    <tr>
     <td bgcolor="#ecf7fd">글내용</td>
     <td bgcolor="f9fafb">
      <textarea name="content" id="content" rows="10" cols="50"></textarea>
     </td>
    </tr>
    
    <tr>
     <td bgcolor="#ecf7fd">암호</td>
     <td bgcolor="f9fafb"><input type="password" name="pw" id="pw" size="10"></td>
    </tr>
    
   <tr>
     <td colspan="2" align="center">
       <input type="submit" value="글쓰기">
       <input type="reset" value="다시작성">
       <input type="button" value="목록보기" onClick="location.href='boardList.do'">
     </td>
     
    </tr>
   </table>
 </form>
</body>
</html>