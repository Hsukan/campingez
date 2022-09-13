<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="캠핑이지" />
</jsp:include>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mypage/mypage.css" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container" id="myTradeList">
	<h3>중고물품 거래목록</h3>
	<table class="table">
		<thead>
			<th scope="col">No</th>
			<th scope="col">거래번호</th>
			<th scope="col">유저 아이디</th>
			<th scope="col">제품카테고리</th>
			<th scope="col">제목</th>
			<th scope="col">내용</th>
			<th scope="col">가격</th>
			<th scope="col">거래상태</th>
			<th scope="col">제품상태</th>
			<th scope="col">좋아요</th>
			<th scope="col">작성일</th>
			<th scope="col">조회수</th>
		</thead>
		<tbody>
			<c:forEach items="${result}" var="trade" varStatus="vs">
				<tr  data-no="${trade.tradeNo}">
					<td>${vs.count}</td>
					<td>${trade.tradeNo}</td>
					<td>${trade.userId}</td>
					<td>${trade.categoryId}</td>
					<td>${trade.tradeTitle}</td>
					<td>
						<div class="tradeContent">
						${trade.tradeContent}
						</div>
					</td>
					<td><fmt:formatNumber value="${trade.tradePrice}" pattern="#,###"/>원</td>
					<td>${trade.tradeSuccess}</td>
					<td>${trade.tradeQuality}</td>
					<td>${trade.likeCount}</td>
					<td>
						<fmt:parseDate value="${trade.tradeDate}" pattern="yyyy-MM-dd" var="resDate" />
						<fmt:formatDate value="${resDate}" pattern="yyyy/MM/dd" />
					</td>
					<td>${trade.readCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</div>
</body>
</html>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>