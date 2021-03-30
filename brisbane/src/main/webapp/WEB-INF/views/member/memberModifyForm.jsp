<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">

	function openDaumPostcode(){
	   new daum.Postcode({
	      oncomplete:function(data){
	         document.getElementById('zipcode').value=data.zonecode;
	         document.getElementById('addr').value=data.address;
	       }
	   }).open();
	}//openDaumPostcode()---

</script>

<script type="text/javascript">
	function check(){
		var inputForm=eval(document.userForm);
		if(!inputForm.pw.value){
			alert("암호를 입력하세요.");
			inputForm.pw.focus();
			return false;
		}
		if(!inputForm.pw2.value){
			alert("암호확인을 입력하세요.");
			inputForm.pw2.focus();
			return false;
		}
		if(inputForm.pw.value!=inputForm.pw2.value){
			alert("암호 확인이 같지 않습니다.");
			inputForm.pw.value="";
			inputForm.pw2.value="";
			inputForm.pw.focus();
			return false;
		}
		if(!inputForm.name.value){
			alert("사용자 이름을 입력하세요.");
			inputForm.name.focus();
			return false;
		}
		
		return true;
	}
</script>
</head>
<body>
<form name="userForm" method="post" action="memberModifyPro.do" onSubmit="return check()">
	<table width="600" cellspacing="0" cellpadding="3">
		<tr>
			<td colspan="2" align="center" bgcolor="#ecf7fd">
				<font size="5"><b>내정보 수정</b></font>
			</td>
 		</tr>
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">ID&nbsp;</td>
 			<td bgcolor="#f9fafb">
 			${memberDto.id}
 				<input type="hidden" name="id" id="id" value="${memberDto.id}">
 			</td>
 		</tr>
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">암호 <font size="-2" color="red">*필수</font></td>
 			<td bgcolor="#f9fafb"><input type="password" name="pw" id="pw" size="10" placeholder="비밀번호 입력"></td>
 		</tr>
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">암호확인 <font size="-2" color="red">*필수</font></td>
 			<td bgcolor="#f9fafb"><input type="password" name="pw2" id="pw2" size="10" placeholder="비밀번호 확인 입력"></td>
 		</tr>
 		
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">이름 <font size="-2" color="red">*필수</font></td>
 			<td bgcolor="#f9fafb"><input type="text" name="name" id="name" value="${memberDto.name}" size="30" placeholder="이름 입력"></td>
 		</tr>
 		
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">주민번호&nbsp;</td>
 			<td bgcolor="#f9fafb"><input type="text" name="jumin1" id="jumin1" size="6" value="${memberDto.jumin1}" placeholder="6자리 입력">
 			- <input type="text" name="jumin2" id="jumin2" size="7" value="${memberDto.jumin2}" placeholder="7자리 입력">
 			</td>
 		</tr>
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">이메일&nbsp;</td>
 			<td bgcolor="#f9fafb"><input type="text" name="email" id="email" size="30" value="${memberDto.email}" placeholder="이메일 입력">
 		
 			</td>
 		</tr>
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">우편번호&nbsp;</td>
 			<td bgcolor="#f9fafb"><input type="text" name="zipcode" id="zipcode" size="9" value="${memberDto.zipcode}" readonly placeholder="주소 검색 선택">
 			<input type="button" value="주소검색" onClick="openDaumPostcode()">
 			</td>
 		</tr>
 		<tr>
 			<td bgcolor="#ecf7fd" align="right">주소&nbsp;</td>
 			<td bgcolor="#f9fafb"><input type="text" name="addr" id="addr" size="50" value="${memberDto.addr}" readonly placeholder="주소 검색 선택"><br>
 			상세주소 : <input type="text" name="addr2" id="addr2" size="50" value="${addr2}" placeholder="상세 주소 입력">
 			</td>
 		</tr>
 		<tr>
 			<td colspan="2" align="center">
				<input type="submit" value="내정보 수정">
				<input type="button" value="취소" onClick="location.href='main.do'">
			</td>
 			
 		</tr>
	</table>
</form>
</body>
</html>

