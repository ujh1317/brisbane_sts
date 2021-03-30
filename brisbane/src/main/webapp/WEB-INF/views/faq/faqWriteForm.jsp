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
   if(document.faqWriteForm.name.value==''){
      alert("글쓴이를 입력 하시오");
      document.faqWriteForm.name.focus();
      return false;
   }
   if(document.faqWriteForm.subject.value==''){
      alert("글제목를 입력 하시오");
      document.faqWriteForm.subject.focus();
      return false;
   }
   if(document.faqWriteForm.content.value==''){
      alert("글내용를 입력 하시오");
      document.faqWriteForm.content.focus();
      return false;
   }
   if(document.faqWriteForm.pass.value==''){
      alert("암호를 입력 하시오");
      document.faqWriteForm.pass.focus();
      return false;
   }
   
   if(document.faqWriteForm.email.value==''){
	      alert("이메일을 입력 하시오");
	      document.faqWriteForm.email.focus();
	      return false;
	   }
   return true;
}

</script>

</head>
<body>
 <h2>Faq 글쓰기</h2>
 <form method="post" name="faqWriteForm" action="faqWritePro.do" onSubmit="return check()">
   <input type="hidden" name="pageNum" value="${pageNum}">
   <input type="hidden" name="num" value="${num}">
   <input type="hidden" name="ref" value="${ref}">
   <input type="hidden" name="re_step" value="${re_step}">
   <input type="hidden" name="re_level" value="${re_level}">
   <input type="hidden" name="question" value="${question}">
   <input type="hidden" name="answer" value="${answer}">
   <table border="1">
   
    <tr>
     <td colspan="2" align="right">
       <a href="faqList.do">글목록</a>
     </td>
    </tr>
    
    <tr>
     <td bgcolor="#ecf7fd">글쓴이</td>
     <td bgcolor="f9fafb"><input type="text" name="name" id="name" size="30"></td>
    </tr>
    
    <tr>
    <td bgcolor="#ecf7fd">이메일</td>
    <td bgcolor="f9fafb"><input type="text" name="email" id="email" size=50>
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
     <td bgcolor="f9fafb"><input type="password" name="pass" id="pass" size="10"></td>
    </tr>
    
   <tr>
     <td colspan="2" align="center">
       <input type="submit" value="글쓰기">
       <input type="reset" value="다시작성">
       <input type="button" value="목록보기" onClick="location.href='faqList.do'">
     </td>
     
    </tr>
   </table>
 </form>
</body>
</html>