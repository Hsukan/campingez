<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	프로필 수정!  
</h1>

<form action="${pageContext.request.contextPath}/mypage/profileUpdate.do" method="POST" enctype="multipart/form-data">
		<table>
			<tr>
				<th>아이디 : </th>
				<td><input type="text"  name="userId" id="userId" value="${result.userId}" readonly required></td>
			</tr>
			<tr>
				<th>이름 : </th>
				<td><input type="text"  name="userName" id="userName" value="${result.userName}" required></td>
				
			</tr>
			<tr>
				<th>패스워드 : </th>
				<td><input type="text"  name="password" id="password" value="${result.password}" required></td>
			</tr>
			<tr>
				<th>이메일 : </th>
				<td><input type="text"  name="email" id="email" value="${result.email}" required></td>
			</tr>
			<tr>
				<th>전화번호 : </th>
				<td><input type="text"  name="phone" id="phone" value="${result.phone}" required></td>
			</tr>
			<tr>
				<th>성별 : </th>
				<td><input type="text"  name="gender" id="gender" value="${result.gender}" ></td>
			</tr>
			<tr>
				<th>경고 횟수 : </th>
				<td><input type="text"  name="yellowCard" id="yellowCard" value="${result.yellowCard}" readonly></td>
			</tr>
			<tr>
				<th>포인트 : </th>
				<td><input type="text"  name="point" id="point" value="${result.point}" readonly></td>
			</tr>
			
		</table>
			<input type="hidden"  name="enabled" id="enabled" value="${result.enabled}" readonly>
			<input type="hidden"  name="enrollType" id="enrollType" value="${result.enrollType}" readonly>
	<input type="submit"  value="정보수정" >
</form>

<script>
	
</script>
</body>
</html>
