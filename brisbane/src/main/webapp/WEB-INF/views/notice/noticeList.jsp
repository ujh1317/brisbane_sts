<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../module/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeList</title>
<link href="resources/member/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h2>list</h2>
	<c:if test="${sessionScope.memId eq 'admin'}">
		<table width="700" align="center">
			<tr>
				<td align="right">
						<a href="${ctxpath}/noticeWrite.do">글쓰기</a>
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${count==0}">
		<table width="700">
			<tr>
				<td align="center">
					게시판에 저장된 글이 없습니다.
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${count>0}">
		<table width="700" align="center">
			<tr bgcolor="#ecf7fd">
				<td width="50" align="center">글번호</td>
				<td align="center">글제목</td>
				<td width="100" align="center">작성자</td>
				<td width="150" align="center">작성일</td>
				<td width="50" align="center">조회수</td>
			</tr>
			
			<c:forEach var="bestList" items="${bestList}">
				<tr bgcolor="#ecf7fd">
					<td align="center">공지</td>
					<td>
						<!-- 답글 -->
						<c:if test="${bestList.re_level>0}">
							<img src="resources/imgs/level.gif" width="${5*bestList.re_level}" height="16">
							<img src="resources/imgs/re.gif">
						</c:if>
						
						<!-- 원글 -->
						<c:if test="${bestList.re_level==0}">
							<img src="resources/imgs/level.gif" width="${5*bestList.re_level}" height="16">
						</c:if>
						
						<!-- 글제목을 클릭하면 글내용보기로 이동 -->
						<c:if test="${!empty sessionScope.memId}">
							<a href="noticeContent.do?num=${bestList.num}&pageNum=${currentPage}">${bestList.subject}</a>
						</c:if>
						<c:if test="${empty sessionScope.memId}">
							${bestList.subject}
						</c:if>
						<c:if test="${bestList.readcount>20}">
							<img src="resources/imgs/hot.gif" border="0" height="16">
						</c:if>
					</td>
					
					<td align="center">${bestList.writer}</td>
					<td align="center"><fmt:formatDate value="${bestList.regdate}" pattern="yyyy-MM-dd"/></td>
					<td align="right">${bestList.readcount}</td>
				</tr>
			</c:forEach>
			
			<c:forEach var="nDto" items="${list}">
				<tr bgcolor="#f9fafb">
					<!-- 글번호 출력 -->
					<td align="center">
						<c:out value="${number}"/>
						<c:set var="number" value="${number-1}"/>
					</td>
					
					<!-- 글 제목 출력 -->
					<td>
						<!-- 답글 -->
						<c:if test="${nDto.re_level>0}">
							<img src="resources/imgs/level.gif" width="${5*nDto.re_level}" height="16">
							<img src="resources/imgs/re.gif">
						</c:if>
						
						<!-- 원글 -->
						<c:if test="${nDto.re_level==0}">
							<img src="resources/imgs/level.gif" width="${5*nDto.re_level}" height="16">
						</c:if>
						
						<!-- 글제목을 클릭하면 글내용보기로 이동 -->
						<c:if test="${!empty sessionScope.memId}">
							<a href="noticeContent.do?num=${nDto.num}&pageNum=${currentPage}">${nDto.subject}</a>
						</c:if>
						<c:if test="${empty sessionScope.memId}">
							${nDto.subject}
						</c:if>
						<c:if test="${nDto.readcount>20}">
							<img src="resources/imgs/hot.gif" border="0" height="16">
						</c:if>
					</td>
					
					<!-- 작성자 출력 -->
					<td align="center">${nDto.writer}</td>
					
					<!-- 작성일 출력 -->
					<td align="center"><fmt:formatDate value="${nDto.regdate}" pattern="yyyy-MM-dd"/></td>
					
					<!-- 조회수 출력 -->
					<td align="right">${nDto.readcount}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<!-- block/page 처리 -->
	<table width="700" align="center">
		<tr>
			<td align="center">
				<!-- 에러방지 -->
				<c:if test="${endPage>pageCount}">
					<c:set var="endPage" value="${pageCount}"/>
				</c:if>
				
				<!-- 이전블럭 -->
				<c:if test="${startPage>10}">
					<a href="noticeList.do?pageNum=${startPage-10}">[이전]</a>
				</c:if>
				
				<!-- page 처리 -->
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<a href="noticeList.do?pageNum=${i}">[${i}]</a>
				</c:forEach>
				
				<!-- 다음블럭 -->
				<c:if test="${endPage<pageCount}">
					<a href="noticeList.do?pageNum=${startPage+10}">[다음]</a>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>