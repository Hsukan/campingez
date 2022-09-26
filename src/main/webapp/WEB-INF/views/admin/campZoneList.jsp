<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8"/>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/views/admin/admin.jsp">
	<jsp:param name="title" value="캠핑이지" />
</jsp:include>
<style>
.table-wrap {
	display: flex;
	justify-content: space-between;
	flex-wrap: wrap;
}
.zone-wrap {
	width: 450px;
	margin-bottom: 25px;
}
</style>
			<div class="content-wrap">
				<h2 class="text-center fw-bold pt-5 pb-5">캠핑 구역 리스트</h2>
				
				<div class="table-wrap">
					<c:forEach items="${campZoneList}" var="zone">
						<div class="zone-wrap">
							<h3>${zone.zoneCode} - ${zone.zoneName}</h3>
							<table class="table text-center table-hover tbl-camp-zone">
									<tr>
										<th>구역코드</th>
										<td>${zone.zoneCode}</td>
									</tr>
									<tr>
										<th>구역이름</th>
										<td>${zone.zoneName}</td>
									</tr>
									<tr>
										<th>구역정보</th>
										<td>
											<c:forEach items="${zone.zoneInfo}" var="info" varStatus="vs">
												${info}${not vs.last ? ", " : ""} ${vs.count % 4 == 0 ? '<br/>' : ""}
											</c:forEach>
										</td>
									</tr>
									<tr>
										<th>허용인원</th>
										<td>${zone.zoneMaximum}명</td>
									</tr>
									<tr>
										<th>구역가격</th>
										<td>
											<fmt:formatNumber value="${zone.zonePrice}" type="currency"/>
										</td>
									</tr>
									<tr>
										<th>관리</th>
										<td>
											<button type="button" id="update-btn" value="${zone.zoneCode}">수정</button>
											<button type="button" id="delete-btn" value="${zone.zoneCode}">삭제</button>
										</td>
									</tr>
							</table>
						</div>
					</c:forEach>
				</div>
				<form:form name="updateOrDeleteFrm">
					<input type="hidden" name="zoneCode"/>
				</form:form>
			</div>
		</div>
	</section>
</main>
<script>
document.querySelectorAll("#update-btn").forEach((btn) => {
	btn.addEventListener('click', (e) => {
		const frm = document.updateOrDeleteFrm;
		const zoneCode = e.target.value;
		
		frm.action = "${pageContext.request.contextPath}/admin/updateCampZone.do";
		frm.method = "GET";
		frm.zoneCode.value = zoneCode;
		frm.submit();
	});
});

document.querySelectorAll("#delete-btn").forEach((btn) => {
	btn.addEventListener('click', (e) => {
		const frm = document.updateOrDeleteFrm;
		const zoneCode = e.target.value;
		
		if(confirm(`[\${zoneCode}]구역을 정말 삭제하시겠습니까?`)) {
			frm.action = "${pageContext.request.contextPath}/admin/deleteCampZone.do";
			frm.method = "POST";
			frm.zoneCode.value = zoneCode;
			frm.submit();
		}
	});
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>