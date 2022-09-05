<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8"/>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form>
	<table>
		<thead>
			<th>NO</th>
			<th>예약번호</th>
			<th>자리아이디</th>
			<th>회원 아이디</th>
			<th>예약자이름</th>
			<th>예약자 전화번호</th>
			<th>예약인원</th>
			<th>금액</th>
			<th>예약일자</th>
			<th>입실일자</th>
			<th>퇴실일자</th>
			<th>차량번호</th>
			<th>예약요청사항</th>
			<th>예약상태</th>
			<th>결제수단</th>
			<th>리뷰작성</th>
		</thead>
		<tbody>
			
			<c:forEach items="${reservationList}" var="res" varStatus="vs">
				<tr data-no="${res.resNo}">
					<td>${vs.count}</td>
					<td>${res.resNo}</td>
					<td>${res.campId}</td>
					<td>${res.userId}</td>
					<td>${res.resUsername}</td>
					<td>${res.resPhone}</td>
					<td>${res.resPerson}</td>
					<td>${res.resPrice}</td>
					<td>
						<fmt:parseDate value="${res.resDate}" pattern="yyyy-MM-dd" var="resDate" />
						<fmt:formatDate value="${resDate}" pattern="yyyy/MM/dd" />
					</td>
					<td>
						<fmt:parseDate value="${res.resCheckin}" pattern="yyyy-MM-dd" var="resCheckin" />
						<fmt:formatDate value="${resCheckin}" pattern="yyyy/MM/dd" />
					</td>
					<td>
						<fmt:parseDate value="${res.resCheckout}" pattern="yyyy-MM-dd" var="resCheckout" />
						<fmt:formatDate value="${resCheckout}" pattern="yyyy/MM/dd" />
					</td>
					<td>${res.resCarNo}</td>
					<td>${res.resRequest}</td>
					<td>${res.resState}</td>
					<td>${res.resPayment}</td>
					<c:if test="${res.review == 'OK'}">
						<td><button  onclick="reservationReview()"/>리뷰작성</td> 
					</c:if> 
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</form:form>
</body>
<script type="text/javascript">
const reservationReview = (e) => {
	const userId = e.target.id;
	const headers = {};
	headers['${_csrf.headerName}'] = '${_csrf.token}';
	
	$.ajax({
		url : `${pageContext.request.contextPath}/admin/warning`,
		headers,
		data : {userId},
		method : "POST",
		success(response) {
			updateUser(e.target);
		},
		error : console.log
	});
};
</script>
</html>