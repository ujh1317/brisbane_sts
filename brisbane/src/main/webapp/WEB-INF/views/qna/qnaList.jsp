<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../module/header.jsp" %>
<fmt:requestEncoding value="UTF-8"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="resources/qna/style.css" rel="stylesheet" type="text/css">
<script>	
	function bounds(){
		alert("비밀글 입니다");
	}
</script>
</head>
<body>

<table width="700" align="center">
	<tr><td colspan="2" align="center"><br><font size="6"><b>Q&A</b></font></td></tr>
	<tr>
		<td align="left">
			<select id="list_category" name="list_category" onChange="var value=list_category.options[list_category.selectedIndex].value;location='qnaList.do?list_category='+value;">
				<option selected value="${list_category}">
				<c:if test="${empty list_category}">
				카테고리 선택
				</c:if>
				
				<c:if test="${!empty list_category}">
					${list_category}
				</c:if>
				</option>
				<option value="전체 글">전체 글</option>
				<option value="회원정보변경">회원정보변경</option>
				<option value="게시물 변경 요청">게시물 변경 요청</option>
				<option value="페이지 오류 신고">페이지 오류 신고</option>
				<option value="기타 문의">기타 문의</option>
			</select>
		</td>
		<td align="right">
			<button><a href="qnaWriteForm.do">글쓰기</a></button>
			
		</td>
	</tr>
</table>

	<c:if test="${count==0}">
		<table border="1"  align="center" width="700">
			<tr>
				<td align="center">
				게시판에 저장된 글이 없습니다.
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${count>0}">
		<table align="center" width="700">
			<tr bgcolor="#ecf7fd">
				<td>글번호</td>
				<td>카테고리</td>
				<td>글제목</td>
				<td>작성자</td>
				<td>조횟수</td>
				<td>작성일</td>

			</tr>
			
			<c:forEach var="dto" items="${qnaList}">
				<tr bgcolor="#f9fafb">
					<td align="center">
						<c:out value="${number}"/>
						<c:set var="number" value="${number+1}"/>
					</td>
					<td>
						${dto.category}
					</td>

					<td>
						<c:if test="${dto.re_level>0}">
						<img src="resources/imgs/level.gif" width="${5*dto.re_level}" height="16"/>
						<img src="resources/imgs/re.gif"/>
						</c:if>
						
						<c:if test="${dto.re_level==0}">
							<img src="resources/imgs/level.gif" width="${5*dto.re_level}" height="16"/>
						</c:if>
						
						<c:if test="${dto.bounds==1}">
							<img src="resources/imgs/bounds.jpg" height="16"/>
							<c:if test="${sessionScope.memId eq dto.writer or sessionScope.memId eq 'admin'}">
								
								<a href="qnaContent.do?num=${dto.num}&pageNum=${pageNum}">
									${dto.subject}
								</a>
							</c:if>
							<c:if test="${sessionScope.memId ne dto.writer and sessionScope.memId ne 'admin'}">
								<a href="javascript:bounds();">
									${dto.subject}
								</a>
							</c:if>
						</c:if>
						<c:if test="${dto.bounds==0}">
							<a href="qnaContent.do?num=${dto.num}&pageNum=${pageNum}">
								${dto.subject}
							</a>
						</c:if>
						
						<c:if test="${dto.readcount>20}">
							<img src="resources/imgs/hot.gif" border="0" height="10"/>
						</c:if>
						
						
					</td>
					
					<td>
						
						${dto.writer}
						
					</td>
					<td>
						${dto.readcount}
					</td>
					<td>
						<c:if test="${dto.regdate eq dto.modifydate}">
							<font size="2">${dto.regdate}</font>
						</c:if>
			
						<c:if test="${dto.regdate ne dto.modifydate}">
							<font size="2">[수정일]${dto.modifydate}</font>
						</c:if>
					</td>


					
				</tr>
			</c:forEach>	
		</table>
	</c:if>	

	<c:if test="${count>0}">
	<table align="center">
	<tr><td>
	
		<c:if test="${endPage>pageCount}">
			<c:set var="endPage" value="${pageCount}"/>
		</c:if>
		<c:if test="${startPage>10}">
			<a href="qnaList.do?pageNum=${startPage-10}">
			[이전 블럭]
			</a>
		</c:if>
		
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="qnaList.do?pageNum=${i}">
			[${i}]
			</a>
		</c:forEach>
		
		<c:if test="${endPage<pageCount}">
			<a href="qnaList.do?pageNum=${startPage+10}">
			[다음 블럭]
			</a>
		</c:if>
		</td></tr>
		</table>
	</c:if>

</body>
</html>