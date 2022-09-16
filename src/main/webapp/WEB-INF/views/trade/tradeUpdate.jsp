<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<sec:authentication property="principal" var="loginMember" scope="page" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="중고거래게시판" name="title" />
</jsp:include>

<section id="trade-container" class="container">

<form name="tradeUpdateFrm" action="${pageContext.request.contextPath}/trade/tradeUpdate.do?${_csrf.parameterName}=${_csrf.token}" method="post" 
enctype="multipart/form-data" >
<input type="hidden" name="tradeNo" value="${trade.tradeNo}" />


<p>글제목: </p><input type="text" name="tradeTitle" value="${trade.tradeTitle}"><br>

<p>작성자: </p><input type="text" name="userId" value="${loginMember.userId}" readonly><br> 


<p>중고물품 구분</p>
<input type="radio" name="categoryId" value="tra1" checked>텐트/타프
<input type="radio" name="categoryId" value="tra2">캠핑 테이블 가구
<input type="radio" name="categoryId" value="tra3">캠핑용 조리도구
<input type="radio" name="categoryId" value="tra4">기타 캠핑용품

<p>상품 상태</p>
<input type="radio" name="tradeQuality" value="S" checked>상태 좋음 (S급)
<input type="radio" name="tradeQuality" value="A">상태 양호 (A급)
<input type="radio" name="tradeQuality" value="B">아쉬운 상태 (B급)

<p>가격</p><input type="text" name="tradePrice" id="tradePrice" value="${trade.tradePrice}"/>

<p>글내용: </p><textarea rows="5" cols="30" name="tradeContent">${trade.tradeContent}</textarea>

<br /><br />
<c:forEach items="${trade.photos}" var="photo" varStatus="vs">
			<div class="btn-group-toggle p-0 mb-3" data-toggle="buttons">
				<label class="btn btn-outline-danger btn-block" style="overflow: hidden" title="">
					<input type="checkbox" id="delFile${vs.count}" name="delFile" value="${photo.tradePhotoNo}">
					첨부파일삭제 - ${photo.originalFilename}
				</label>
			</div>
		</c:forEach>

<div class="input-group-prepend" style="padding:0px;">
		    <span class="input-group-text">첨부파일1</span>
		  </div>
		  <div class="custom-file">
		    <input type="file" class="custom-file-input" name="upFile" id="upFile1" accept="image/*" multiple>
		    <label class="custom-file-label" for="upFile1">파일을 선택하세요</label>
		  </div>
<br><br>
<input type="submit" value="저장">
</form>



</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />