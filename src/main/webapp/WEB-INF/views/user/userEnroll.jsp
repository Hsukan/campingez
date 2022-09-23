﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="회원가입" name="title" />
</jsp:include>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<style>
    .correct {color: blue;}
    .incorrect { color: red; }
    sup{color: red;}
    th{text-align: left; width: 130px;}
    td{padding: 10px; width: 250px;}
    .rule-header-wrap {
    	display:flex;
    }
    .rule-wrap {
    	margin: 0 auto;
    	width: 650px;
    }
    .allCheck {
	    width: 650px;
	    height: 30px;
	    display: flex;
	    align-items: center;
	    margin: 0 auto;
	}
    .form-check-input {
    	width: 30px;
    	height: 30px;
    }
    .label {
   	    padding-left: 22px;
    }
    #utilization {
    	height: 300px;
   	    font-size: 12px;	
    }
    #all-check {
     border-radius: 8px;
     width:30px;
    }
    #rule-check {
    	width:32px;
    }
  </style>
  <br />
 <div id="enroll-container" class="mx-auto text-center container">
    <h1>회원가입</h1><br>
    <form:form name="userEnrollFrm" action="" method="POST">
      <table class="mx-auto">
        <tr>
          <th>아이디<sup>*</sup></th>
          <td>
            <div id="userId-container">
              <input type="text" class="form-control" placeholder="아이디(4글자이상)" name="userId" id="userId" required
                oninput="checkId()">
               <input type="hidden" name="idCheckVal" id="idCheckVal" value="0"/>
            </div>
          </td>
          <td>
            <div>
              <span id="msguserId" class="msg"></span>
            </div>
          </td>
        </tr><tr><th>&nbsp;</th></tr>
        <tr>
          <th>이름<sup>*</sup></th>
          <td><input type="text" class="form-control" name="userName" id="userName" value="" required></td>
        </tr><tr><th>&nbsp;</th></tr>
        <tr>
          <th>비밀번호<sup>*</sup></th>
          <td><input type="password" class="form-control" name="password" id="password" value="" required
              oninput="passwordCK()"></td>
          <td>
            <div>
              <span id="msgPassword" class="msg"></span>
            </div>
          </td>
        </tr>
        	<tr>
        		<th>&nbsp;</th>
        		<td colspan="2" style="font-size: 0.85em; text-align: left;">
        			<sup>**</sup>비밀번호는 영문자+숫자+특수문자(!@#$%^&*) 포함 8글자이상<sup>**</sup>
        		</td>
        	</tr>
        <tr>
          <th>패스워드확인</th>
          <td><input type="password" class="form-control" id="passwordCheck" value="" required></td>
          <td>
            <div>
              <span id="msgPasswordCheck" class="msg"></span>
            </div>
          </td>
        </tr><tr><th>&nbsp;</th></tr>
        <tr>
          <th>이메일<sup>*</sup></th>
          <td><input type="email" class="form-control" placeholder="abc@xyz.com" name="email" id="email"
              value="" required oninput="emailCK()"></td>
          <td>
            <button type="button" class="btn btn-primary" id="mail-Check-Btn" style="width: 100%;" disabled>이메일인증</button>
          </td>
        </tr><tr><th>&nbsp;</th></tr>
        <tr>
          <th>이메일 확인 </th>
          <td>
            <input class="form-control mail-check-input" placeholder="인증번호 6자리" disabled="disabled">
          </td>
          <td colspan="3">
            <span id="mail-check-warn"></span>
            <input type="hidden" name="mailCheckVal" id="mailCheckVal" value="0" />
          </td>
        </tr><tr><th>&nbsp;</th></tr>
        <tr>
          <th>휴대폰<sup>*</sup></th>
          <td><input type="tel" class="form-control" placeholder="(-없이)01012345678" name="phone" id="phone"
              maxlength="11" value="" required></td>
        </tr><tr><th>&nbsp;</th></tr>
        <tr>
          <th>성별</th>
          <td>
              <input type="radio" class="form-check-input" name="gender" id="gender0" value="M">
              <label class="form-check-label" for="gender0">남</label> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
              <input type="radio" class="form-check-input" name="gender" id="gender1" value="F"> 
              <label class="form-check-label" for="gender1">여</label>
          </td>
        </tr><tr><th>&nbsp;</th></tr>
      </table>
         <div class="accordion accordion-flush rule-wrap" id="accordionFlushExample">
		  <div class="accordion-item">
		    <h2 class="accordion-header rule-header-wrap" id="flush-headingOne">
		      <input class="form-check-input" type="checkbox" id="rule-check" name="default-check">
		      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
		        이용약관 동의(필수)
		      </button>
		    </h2>
		    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
		      <div class="accordion-body">
		      	<div class="rule-wrap form-floating">
		      		<textarea class="form-control" id="utilization" cols="100" rows="10">
제1장 총 칙
					
제1조 (목적)
						
이 약관(이하 “약관”이라 합니다)은 캠핑이지(이하 “회사”라 합니다)와 이용 고객(이하 “회원”이라 합니다)간에 회사가 제공하는 서비스(이하 “서비스”라 합니다)를 이용함에 있어 회원과 회사간의 권리, 의무 및 책임사항, 이용조건 및 절차 등 기본적인 사항을 규정함을 목적으로 합니다.
						
제2조 (용어의 정의)
본 약관에서 사용되는 용어의 정의는 다음과 같습니다.
						
(1) “서비스” : 회사가 제공하는 여가(숙박∙레저∙티켓∙클래스 등으로 이에 한정되지 않음) 관련 상품∙용역∙서비스 등(이하 “상품등”이라 합니다)에 대한 예약∙구매∙정보제공∙추천 등을 위한 온라인 플랫폼 서비스를 의미합니다. 서비스는 구현되는 장치나 단말기(PC, TV, 휴대형 단말기 등의 각종 유무선 장치를 포함하며 이에 한정되지 않음)와 상관 없이 미리해 및 미리해 관련 웹(Web)∙앱(App) 등의 제반 서비스를 의미하며, 회사가 공개한 API를 이용하여 제3자가 개발 또는 구축한 프로그램이나 서비스를 통하여 이용자에게 제공되는 경우를 포함합니다. 서비스는 여가 문화가 변하고 기술이 발전함에 따라 지속적으로 함께 변화∙성장해 나갑니다.
(2) “회원” : 서비스에 접속하여 본 약관에 따라 회사와 이용계약을 체결하고 회사가 제공하는 서비스를 이용하는 고객을 말하며, 회원계정(ID/PW)을 생성하지 않은 일반 고객(일명 비회원 고객)도 포함됩니다.
(3) “판매자” : 회사가 제공하는 서비스를 이용하여 자신의 상품∙용역∙서비스 등을 판매하는 자를 의미하며, 회사로부터 예약∙판매 대행, 광고 서비스 등을 제공받는 자를 말합니다.
(4) “게시물” : 회원 및 이용 고객이 서비스에 게시 또는 등록하는 부호(URL 포함), 문자, 음성, 음향, 영상(동영상 포함), 이미지(사진 포함), 파일 등을 말합니다.
(5) “쿠폰” : 회원이 서비스를 이용할 때 표시된 금액 또는 비율만큼 이용 금액을 할인 받을 수 있는 할인권∙우대권 등(온라인∙모바일∙오프라인 등 형태를 불문)를 말합니다. 쿠폰의 종류 및 내용은 회사의 정책에 따라 달라질 수 있습니다.
(6) “포인트” : 회사가 회원의 서비스 이용에 따른 혜택 또는 서비스 이용상 편의를 위해 회원에게 제공하는 것으로서 서비스 내 상품 등 결제시 활용할 수 있는 수치화 된 가상의 데이터를 말합니다. 구체적인 이용 방법, 그 명칭(예: 마일리지 등) 및 현금 환급가능성 등은 회사의 정책에 따라 달라질 수 있습니다.
(7) "더해 코인" : 포인트로 교환하여 사용할 수 있는 수치화 된 가상의 데이터를 말합니다. 구체적인 이용 방법, 그 명칭 등은 회사의 정책에 따라 달라질 수 있습니다.

제3조 (약관의 효력 및 변경)
① 본 약관은 서비스를 이용하고자 하는 모든 회원에 대하여 그 효력을 발생합니다. 단, 일부 특정 서비스'의 경우 본 약관이 아닌 회사에 해당 서비스를 제공하는 사업자의 약관이 적용됩니다(자세한 안내는 각 서비스 영역에 별도 표시).
② 본 약관은 회원이 서비스 가입 시 열람 할 수 있으며, 회사는 회원이 원할 때 언제든지 약관을 열람 할 수 있도록 회사 홈페이지 또는 앱 내에 게시합니다.
③ 회사는 『약관의 규제에 관한 법률』, 『전자상거래 등에서의 소비자보호에 관한 법률』, 『정보통신망 이용촉진 및 정보보호(이하 “정보통신망법”이라 합니다)』, , 『소비자기본법』, 『전자문서 및 전자거래 기본법』 등 관련법에 위배되지 않는 범위 내에서 본 약관을 개정할 수 있습니다.
④ 회사가 약관을 변경할 경우에는 적용일자, 변경사유를 명시하여 적용일로부터 7일 이전부터 공지합니다. 다만, 회원에게 불리한 약관 변경인 경우에는 그 적용일로부터 30일전부터 위와 같은 방법으로 공지하고, E-mail 등으로 회원에게 개별 통지합니다. 단, 회원의 연락처 미기재, 변경 후 미수정 등으로 인하여 개별 통지가 어려운 경우 공지를 개별 통지로 간주합니다.
⑤ 회사가 제4항에 따라 개정약관을 공지 또는 통지하면서, 회원에게 약관 변경 적용일 까지 거부의사를 표시하지 않으면 약관의 변경에 동의한 것으로 간주한다는 내용을 공지 또는 통지하였음에도 회원이 명시적으로 약관 변경에 대한 거부의사를 표시하지 아니하는 경우 회원이 개정약관에 동의한 것으로 간주합니다.
⑥ 회원은 변경된 약관에 동의하지 아니하는 경우 서비스의 이용을 중단하고 이용계약을 해지할 수 있습니다.
⑦ 회원은 약관 변경에 대하여 주의의무를 다하여야 하며, 변경된 약관의 부지로 인한 회원의 피해는 회사가 책임지지 않습니다.

제4조 (약관 외 준칙 및 관련법령과의 관계)
① 본 약관 또는 개별약관에서 정하지 않은 사항은 『전기통신사업법』, 『전자문서 및 전자거래 기본법』, 『정보통신망 이용촉진 및 정보보호 등에 관한 법률』, 『전자상거래 등에서의 소비자보호에 관한 법률』, 『개인정보보호법』 등 관련 법의 규정 및 회사가 정한 서비스의 세부이용지침 등의 규정에 따릅니다. 또한 본 약관에서 정한 회사의 책임 제한 규정은 관련 법령이 허용하는 최대한도의 범위 내에서 적용됩니다.
② 회사는 필요한 경우 서비스 내의 개별항목에 대하여 개별약관 또는 운영원칙을 정할 수 있으며, 본 약관과 개별약관 또는 운영원칙의 내용이 상충되는 경우에는 개별약관 또는 운영원칙을 의 내용이 우선 적용됩니다.


제2장 이용계약의 체결

제5조 (이용계약의 성립)
① 이용계약은 회원이 되고자 하는 자(이하 “가입신청자”)가 약관의 내용에 동의를 하고, 회사가 정한 가입 양식에 따라 회원정보를 기입하여 회원가입신청을 하고 회사가 이러한 신청에 대하여 승인함으로써 체결됩니다.
② 회원은 다수의 아이디(ID)를 생성하여 사용할 수 있으나, 본인인증이 필요한 일부 서비스의 경우에는 최초 본인인증된 1개 아이디(ID)로만 이용이 가능합니다.

제6조 (이용계약의 유보 및 거절)
① 회사는 다음 각 호에 해당하는 신청에 대하여는 승인 하지 않거나 사후에 이용계약을 해지할 수 있습니다.
  (1) 가입신청서의 내용을 허위로 기재하거나 기재누락∙오기가 있는 경우
  (2) 다른 사람의 명의, E-mail, 연락처 등을 이용한 경우
  (3) 관계법령에 위배되거나 사회의 안녕질서 또는 미풍양속을 저해할 목적으로 신청한 경우
  (4) 가입신청자가 본 약관에 의하여 이전에 회원자격을 상실한 적이 있는 경우, 단 회사의 사전재가입 승낙을 얻은 경우에는 예외로 함
  (5) 회원의 귀책사유로 인하여 승인이 불가능한 경우
  (6) 이미 가입된 회원과 전화번호나 E-mail 정보가 동일한 경우
  (7) 부정한 용도 또는 영리를 추구할 목적으로 본 서비스를 이용하고자 하는 경우
  (8) 기타 본 약관에 위배되거나 위법 또는 부당한 이용신청임이 확인된 경우 및 회사가 합리적인 판단에 의하여 필요하다고 인정하는 경우
  (9) 만 14세 미만의 아동이 신청할 경우
② 제1항에 따른 신청에 있어 회사는 전문기관을 통한 휴대전화번호 인증 내지 실명확인을 통한 본인인증을 요청할 수 있습니다.
③ 회사는 아래 각 호에 해당하는 경우에는 회원등록의 승낙을 유보할 수 있습니다.
  (1) 제공서비스 설비용량에 현실적인 여유가 없는 경우
  (2) 서비스를 제공하기에는 기술적으로 문제가 있다고 판단되는 경우
  (3) 회사가 재정적, 기술적으로 필요하다고 인정하는 경우
  (4) 제2항에 따른 실명확인 및 본인인증 절차를 진행 중인 경우
④ 제1항과 제3항에 따라 회원가입신청의 승낙을 하지 아니하거나 유보한 경우, 회사는 승낙거절 또는 유보 사실을 가입신청자에게 회사가 정하는 방법에 따라 통지합니다.
⑤ 이용계약의 성립 시기는 회사가 ‘가입완료’ 사실을 신청절차 상에 표시하거나 별도로 통지한 시점으로 합니다.

제7조 (회원 정보의 관리)
① 회원은 웹사이트 또는 앱 내에 구비된 정보수정 기능 내지 고객센터 등을 통해 개인정보를 열람∙변경∙수정 할 수 있습니다. 다만, 일부 개인정보의 경우 본인인증 절차가 필요할 수 있습니다.
② 회원은 등록한 E-mail 주소 또는 연락처가 변경된 경우 회원정보의 최신성 유지를 위해 제1항에 따라 변경해야 하며 이를 변경하지 않아 발생 할 수 있는 모든 불이익은 회원이 부담합니다.

제8조 (계정정보의 관리책임)
① 아이디, 비밀번호 등 계정정보의 관리책임은 회원에게 있으며, 회원은 계정정보를 타인에게 양도 내지 대여할 수 없습니다.
② 회사는 회사의 귀책사유로 인한 경우를 제외하고 계정정보의 유출, 양도, 대여, 공유 등으로 인한 손실이나 손해에 대하여 아무런 책임을 지지 않습니다.
③ 회원은 제3자가 본인의 계정을 사용하고 있음(대여 포함)을 인지한 경우에는 즉시 비밀번호를 수정하는 등의 조치를 취하고 이를 회사에 통보하여야 합니다. 회원이 본항에 따른 통지를 하지 아니하여 발생하는 모든 불이익에 대한 책임은 회원에게 있습니다.

제9조 (회원정보의 수집과 보호)
① 회사는 서비스를 제공함에 있어 개인정보 관련 법령을 준수하고 그에 따라 회원 정보를 수집∙이용∙보관∙제공합니다.
② 회사는 회원이 서비스 이용 과정에서 직접 제공한 정보 외에도 개인정보 보호 등 관련 법령에서 정한 절차에 따라 그밖의 정보를 수집 및 이용 또는 제3자에게 제공할 수 있습니다. 이 경우 회사는 관련 법령에 따라 회원으로부터 필요한 동의를 받거나 관련 법령에서 정한 절차를 준수합니다.
③ 회사는 개인정보 보호 관련 법령이 정하는 바에 따라 회원의 개인정보를 보호하기 위해 노력하며, 회사의 개인정보 처리에 관한 자세한 사항은 개인정보처리방침을 통해 언제든지 확인할 수 있습니다.
④ 회사의 공식 사이트 또는 앱 이외에 링크된 사이트에서는 회사의 개인정보처리방침이 적용되지 않습니다. 링크된 사이트 및 서비스를 제공하는 제3자의 개인정보 처리에 대해서는 해당 사이트 및 제3자의 개인정보처리방침을 확인할 책임이 회원에게 있으며, 회사는 이에 대하여 책임을 부담하지 않습니다.


제3장 서비스 이용

제10조 (서비스의 이용 개시)
회원은 회사가 이용신청을 승낙한 때부터 서비스를 사용할 수 있고 회사는 위 승낙한 때부터 본약관에 따라 서비스를 제공합니다.

제11조 (서비스의 이용시간)
① 서비스는 회사의 업무상 또는 기술상 특별한 사유가 없는 한 연중무휴 1일 24시간 제공함을 원칙으로 합니다. 회사는 서비스를 일정범위로 분할하여 각 범위 별로 이용가능시간을 별도로 지정할 수 있습니다.
② 회사는 서비스의 제공에 필요한 경우 정기점검 내지 수시점검을 실시할 수 있으며, 정기점검 내지 수시점검 시간은 서비스제공화면에 공지한 바에 따릅니다.

제12조 (서비스의 내용)
① 서비스는 제2조 제1호에서 정의한 바에 따라 회사가 회원에게 제공하는 여가 관련 온라인 플랫폼 서비스를 말합니다. 서비스는 현재 제공되는 상품등에 한정되지 않으며, 향후 추가로 개발되거나 다른 회사와의 제휴 등을 통해 추가∙변경∙수정될 수 있고, 이 과정에서 일부 서비스의 경우 본인 인증 절차를 요구할 수 있습니다.
② 회사는 회원 감소 등으로 인한 원활한 서비스 제공의 곤란 및 수익성 악화, 기술 진보에 따른 차세대 서비스로의 전환 필요성, 서비스 제공과 관련한 회사 정책의 변경 등 기타 상당한 이유가 있는 경우에 운영상, 기술상의 필요에 따라 제공하고 있는 전부 또는 일부 서비스를 변경 또는 중단할 수 있습니다.
③ 서비스의 내용, 이용방법, 이용시간에 대하여 변경 또는 서비스 중단이 있는 경우에는 변경 또는 중단될 서비스의 내용 및 사유와 일자 등은 그 변경 또는 중단 전에 회사 홈페이지 또는 서비스 내 "공지사항" 화면 등 회원이 충분히 인지할 수 있는 방법으로 30일의 기간을 두고 사전에 공지합니다.
④ 무료로 제공되는 서비스의 특성상 본조에 따른 서비스의 전부 또는 일부 종료 시 관련법령에서 특별히 정하지 않는 한 회원에게 별도의 보상을 하지 않습니다.
⑤ 서비스 이용에 관한 개별 안내, 상품등에 대한 정보, 예약시 유의사항, 취소∙환불정책 등은 개별 서비스 이용안내∙소개 등을 통해 제공하고 있습니다.
⑥ 회원은 전항의 안내∙소개 등을 충분히 숙지하고 서비스를 이용하여야 합니다. 회사는 통신판매중개자로서 통신판매의 당사자가 아니고, 판매자가 상품등 이용에 관한 이용정책이나 예약에 관한 취소∙환불정책을 별도로 운영하는 경우가 있을 수 있으므로, 회원은 상품등 이용 또는 예약시 반드시 그 내용을 확인해야 합니다. 회원이 이 내용을 제대로 숙지하지 못해 발생한 피해에 대해서는 회사가 책임을 부담하지 않습니다.

제13조 (서비스의 변경 및 중지)
① 회사는 변경될 서비스의 내용 및 제공일자를 본 약관 제22조에서 정한 방법으로 회원에게 고지하고 서비스를 변경하여 제공할 수 있습니다.
② 회사는 다음 각 호에 해당하는 경우 서비스의 전부 또는 일부를 제한하거나 중지할 수 있습니다.
  (1) 일부 서비스를 이용함에 있어 요구되는 본인인증 절차를 거치지 않거나, 본인인증 정보가 위조·변조·허위의 정보임이 확인된 경우
  (2) 회원이 회사의 영업활동을 방해하는 경우
  (3) 시스템 정기점검, 서버의 증설 및 교체, 네트워크의 불안정 등의 시스템 운영상 필요한 경우
  (4) 정전, 서비스 설비의 장애, 서비스 이용폭주, 기간통신사업자의 설비 보수 또는 점검∙중지 등으로 인하여 정상적인 서비스 제공이 불가능한 경우
  (5) 기타 중대한 사유로 인하여 회사가 서비스 제공을 지속하는 것이 부적당하다고 인정하는 경우
  (6) 기타 천재지변, 국가비상사태 등 불가항력적 사유가 있는 경우
③ 제2항에 의한 서비스 중단의 경우 회사는 제22조에서 정한 방법으로 회원에게 통지합니다. 다만, 회사가 통제할 수 없는 사유로 인한 서비스의 중단(운영자의 고의∙과실이 없는 장애, 시스템다운 등)으로 인하여 사전 통지가 불가능한 경우에는 그러하지 아니합니다.
④ 회사는 제2항에 따른 서비스의 변경, 중지로 발생하는 문제에 대해서 어떠한 책임도 지지 않습니다.

제14조 (정보 제공 및 광고의 게재)
① 회사가 회원에게 서비스를 제공할 수 있는 서비스 투자기반의 일부는 광고 게재를 통한 수익으로부터 나옵니다. 이에 회원은 서비스 이용 시 서비스 화면 상 노출되는 광고 게재에 대해 동의합니다.
② 회사는 회원의 게시물 내용, 검색 내용 뿐만 아니라 언어, 쿠키 및 기기정보, IP 주소, 브라우저 유형, 운영체제 및 요청 일시와 같은 표준 로그 정보 등을 활용한 맞춤광고를 제공합니다. 이에 대한 자세한 사항은 "개인정보처리방침"을 참고하시기 바랍니다.
③ 회원이 서비스상에 게재되어 있는 광고를 이용하거나 서비스를 통한 광고주의 판촉활동에 다른 상품등을 이용하는 경우, 이는 전적으로 회원과 광고주 간의 법률관계이므로, 그로 인해 발생한 회원과 광고주간 분쟁 등 제반 문제는 회원과 광고주간에 직접 해결하여야 하며, 이와 관련하여 회사는 어떠한 책임도 지지 않습니다.
④ 회사는 회원으로부터 수집한 개인정보를 통해 회원의 서비스 이용 중 필요하다고 판단되는 다양한 마케팅 정보를 SMS(LMS), 스마트폰 알림 (Push 알림), E-mail 등을 활용하여 발송할 수 있으며, 회원은 이에 동의합니다. 다만, 회원은 거래관련정보 및 고객문의 등에 대한 답변을 제외하고 관련법에 따라 언제든지 마케팅 수신 동의를 철회하실 수 있으며, 이 경우 회사는 전단의 마케팅 정보 등을 제공하는 행위를 중단합니다(단, 시스템 반영에 시차가 있을 수 있음).

제15조 (게시물에 대한 권리)
① 게시물의 저작권은 회원에게 있습니다. 다만 회사는 게시∙전달∙공유 목적으로 회원이 작성한 게시물을 이용∙편집∙수정하여 이용할 수 있고, 회사의 다른 서비스 또는 연동채널∙판매채널에 이를 게재하거나 활용할 수 있습니다.
② 회사가 서비스 내 게재 이외의 목적으로 게시물을 사용할 경우 게시물에 대한 게시자를 반드시 명시해야 합니다. 단, 게시자를 알 수 없는 익명 게시물이나 비영리적인 경우에는 그러하지 아니합니다.
③ 회원은 게시물을 작성할 때 타인의 저작권 등 지식재산권을 포함하여 여타 권리를 침해하지 않아야 하고, 회사는 그에 대한 어떠한 책임도 부담하지 않습니다. 만일 회원이 타인의 권리 등을 침해하였음을 이유로 타인이 회사를 상대로 이의신청, 손해배상청구, 삭제요청 등을 제기한 경우 회사는 그에 필요한 조치를 취할 수 있으며, 그에 따른 모든 비용이나 손해배상책임은 회원이 부담합니다.
④ 회사는 회원이 이용계약을 해지하고 사이트를 탈퇴하거나 적법한 사유로 해지된 경우 해당 회원이 게시하였던 게시물을 삭제할 수 있습니다.
⑤ 회원이 작성한 게시물에 대한 모든 권리 및 책임은 이를 게시한 회원에게 있으며, 회사는 회원이 게시하거나 등록한 게시물이 다음 각 호에 해당한다고 판단되는 경우에 사전통지 없이 삭제 또는 임시조치할 수 있고, 이에 대하여 회사는 어떠한 책임도 지지 않습니다.
  (1) 다른 회원 또는 제3자를 비방하거나 명예를 손상시키는 내용인 경우
  (2) 공공질서 및 미풍양속에 위반되는 내용을 유포하거나 링크시키는 경우
  (3) 불법복제 또는 해킹을 조장하는 내용인 경우
  (4) 회사로부터 사전 승인 받지 않고 상업광고, 판촉 내용을 게시하는 경우
  (5) 개인 간 금전거래를 요하는 경우
  (6) 범죄적 행위에 결부된다고 인정되는 경우
  (7) 회사의 저작권, 제3자의 저작권 등 기타 권리를 침해하는 내용인 경우
  (8) 타인의 계정정보, 성명 등을 무단으로 도용하여 작성한 내용이거나, 타인이 입력한 정보를 무단으로 위변조한 내용인 경우
  (9) 사적인 정치적 판단이나 종교적 견해의 내용으로 회사가 서비스 성격에 부합하지 않는다고 판단하는 경우
  (10) 동일한 내용을 중복하여 다수 게시하는 등 게시의 목적에 어긋나는 경우
  (11) 회사에서 규정한 게시물 원칙에 어긋나거나, 게시물을 작성하는 위치에 부여된 성격에 부합하지 않는 경우
  (12) 사업주 변경 또는 인테리어 공사 등에 따른 권리자(사업주)의 게시물 중단 또는 삭제 요청이 있는 경우
  (13) 기타 관계법령에 위배된다고 판단되는 경우
⑥ 회원의 게시물이 정보통신망법 및 저작권법 등 관련법에 위반되는 내용을 포함하는 경우, 권리자는 관련법이 정한 절차에 따라 해당 게시물의 게시중단 및 삭제 등을 요청할 수 있으며, 회사는 관련법에 따라 조치를 취하여야 합니다.
⑦ 회사는 본조 제2항에 따른 권리자의 요청이 없는 경우라도 권리침해가 인정될 만한 사유가 있거나 기타 회사 정책 및 관련법에 위반되는 경우에는 관련법에 따라 해당 "게시물"에 대해 임시조치 등을 취할 수 있습니다.
⑧ 본 조에 따른 세부절차는 정보통신망법 및 저작권법이 규정한 범위 내에서 회사가 정한 게시중단요청서비스에 따릅니다.
- 게시중단요청 : help@campingez.co.kr

제16조 (권리의 귀속)
① 서비스에 대한 저작권 및 지식재산권은 회사에 귀속됩니다. 단, 게시물 및 제휴계약에 따라 제공된 저작물 등은 제외합니다.
② 회사가 제공하는 서비스의 디자인, 회사가 만든 텍스트, 스크립트(script), 그래픽, 회원 상호간 전송 기능 등 회사가 제공하는 서비스에 관련된 모든 상표, 서비스 마크, 로고 등에 관한 저작권 기타 지적재산권은 대한민국 및 외국의 법령에 기하여 회사가 보유하고 있거나 회사에게 소유권 또는 사용권이 있습니다.
③ 회원은 본 이용약관으로 인하여 서비스를 소유하거나 서비스에 관한 저작권을 보유하게 되는 것이 아니라, 회사로부터 서비스의 이용을 허락 받게 되는바, 서비스는 정보취득 또는 개인용도로만 제공되는 형태로 회원이 이용할 수 있습니다.
④ 회원은 명시적으로 허락된 내용을 제외하고는 서비스를 통해 얻어지는 회원 상태정보를 영리 목적으로 사용, 복사, 유통하는 것을 포함하여 회사가 만든 텍스트, 스크립트, 그래픽의 회원 상호간 전송기능 등을 복사하거나 유통할 수 없습니다.
⑤ 회사는 서비스와 관련하여 회원에게 회사가 정한 이용조건에 따라 계정, ID, 콘텐츠 등을 이용할 수 있는 이용권만을 부여하며, 회원은 이를 양도, 판매, 담보제공 등의 처분행위를 할 수 없습니다.
⑥ 회원은 서비스를 이용하는 과정에서 얻은 정보를 회사의 사전 승낙 없이 복제, 송신, 출판, 배포, 방송, 편집, 재가공 등 기타 방법에 의하여 영리 목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.

제17조 (포인트)
① 포인트는 회사의 정책에 따라 회원에게 부여되며, 포인트별 적립기준, 사용방법, 사용기한 및 제한에 관한 사항은 별도로 공지하거나 통지합니다. 단, 포인트 사용기한에 대해 별도로 안내하지 않은 경우에는 1년으로 봅니다.
② 포인트는 사용기한 동안 사용되지 않거나 회원 탈퇴 또는 자격상실 사유가 발생한 경우 자동으로 소멸됩니다. 회원 탈퇴 내지 자격상실로 포인트가 소멸된 경우 재가입하더라도 소멸된 포인트는 복구되지 않습니다.
③ 회원은 회사가 별도로 명시한 경우를 제외하고는 포인트를 제3자에게 양도 할 수 없습니다. 만일 회원이 회사가 승인하지 않은 방법으로 포인트 획득/이용한 사실이 확인될 경우 회사는 포인트를 사용한 예약 신청을 취소하거나 회원 자격을 정지 또는 해지할 수 있습니다.
④ 포인트 관련 회사의 정책은 회사의 영업정책에 따라 변동될 수 있습니다. 회원에게 불리한 변경인 경우에는 제3조의 규정에 따라 공지 또는 통지하며 서비스 계속 이용시 동의한 것으로 간주됩니다.

제18조 (쿠폰)
① 쿠폰은 회사가 유상 또는 무상으로 발행하는 쿠폰으로 발행대상, 발행경로, 사용대상 등에 따라 구분될 수 있으며, 쿠폰의 세부구분, 할인금액(할인율), 사용방법, 사용기간 및 제한에 대한 사항은 쿠폰 또는 서비스 화면에 표시됩니다. 쿠폰의 종류 및 내용과 발급여부에 관하여는 회사의 영업정책에 따라 달라질 수 있습니다.
② 쿠폰은 현금으로 출금될 수 없으며, 쿠폰에 표시된 사용기간이 만료되거나 이용계약이 종료되면 소멸합니다.
③ 예약거래가 취소될 경우 예약에 이용된 쿠폰은 회사의 정책에 따라 환원 여부가 결정되며 자세한 사항은 유선 고지 내지 예약서비스 화면을 통해 안내됩니다.
④ 회원은 회사가 별도로 명시한 경우를 제외하고는 쿠폰을 제3자에게 또는 다른 아이디로 양도 할 수 없으며 유상으로 거래하거나 현금으로 전환 할 수 없습니다. 만일 회원이 회사가 승인하지 않은 방법으로 부정한 방법으로 쿠폰을 획득/이용한 사실이 확인될 경우 회사는 회원의 쿠폰을 사용한 예약 신청을 취소하거나 회원 자격을 정지 또는 해지할 수 있습니다.
⑤ 쿠폰 관련 회사의 정책은 회사의 영업정책에 따라 변동될 수 있습니다. 회원에게 불리한 변경인 경우에는 제3조의 규정에 따라 공지 또는 통지하며 서비스 계속 이용시 동의한 것으로 간주됩니다.

제19조 (더해 코인)
① 더해 코인은 회사의 정책에 따라 회원에게 제공되며, 코인별 적립기준, 사용방법, 사용기한 및 제한에 관한 사항은 회사 정책에 따라 별도 공지합니다.
② 더해 코인은 사용기한 동안 사용되지 않거나 회원 탈퇴 또는 자격상실 사유가 발생한 경우 자동으로 소멸됩니다. 회원 탈퇴 내지 자격상실로 더해 코인이 소멸된 경우 재 가입하더라도 소멸된 더해 코인은 복구되지 않습니다.
③ 회원은 회사가 별도로 정한 경우를 제외하고는 더해 코인을 제3자에게 양도할 수 없습니다. 만일 회원이 회사가 승인하지 않은 방법으로 더해 코인을 획득/이용한 사실이 확인될 경우 회사는 회원 자격을 정지 또는 해지할 수 있습니다.
④ 더해 코인 관련 회사의 정책은 회사의 영업정책에 따라 변동될 수 있습니다. 회원에게 불리한 변경인 경우에는 제3조의 규정에 따라 공지 또는 통지하며 서비스 계속 이용시 동의한 것으로 간주됩니다.


제4장 계약당사자의 의무
제20조 (회사의 의무)
① 회사는 관련법과 본 약관이 금지하거나 미풍양속에 반하는 행위를 하지 않으며, 계속적이고 안정적으로 서비스를 제공하기 위하여 최선을 다하여 노력합니다.
② 회사는 회원이 안전하게 서비스를 이용할 수 있도록 개인정보보호를 위해 보안시스템을 갖추어야 하며 개인정보처리방침을 공시하고 준수하며, 회원의 개인정보를 본인의 승낙 없이 제3자에게 누설, 배포하지 않고, 이를 보호하기 위하여 노력합니다.
③ 회사는 서비스이용과 관련하여 회원으로부터 제기된 의견이나 불만이 정당하다고 인정할 경우에는 그에 필요한 조치를 취하여야 합니다.
④ 회사가 제공하는 서비스로 인하여 회원에게 손해가 발생한 경우 그러한 손해가 회사의 고의나 과실에 의해 발생한 경우에 한하여 회사에서 책임을 부담하며, 그 책임의 범위는 통상손해에 한합니다.

제21조 (회원의 의무)
① 회원은 기타 관계 법령, 본 약관의 규정, 이용안내 및 서비스상에 공지한 주의사항, 회사가 통지하는 사항 등을 준수하여야 하며, 기타 회사의 업무에 방해되는 행위를 하여서는 아니 됩니다.
② 회원은 서비스의 이용권한, 기타 서비스 이용계약상의 지위를 타인에게 양도, 증여할 수 없으며 이를 담보로 제공할 수 없습니다.
③ 회원은 서비스 이용과 관련하여 다음 각 호의 행위를 하여서는 안됩니다.
  (1) 서비스 신청 변경, 본인인증 등 서비스 이용과정에서 허위내용을 기재·등록·전송 등을 하는 행위
  (2) 다른 회원의 아이디 및 비밀번호를 도용하여 부당하게 서비스를 이용하거나, 정보를 도용하는 행위
  (3) 타인의 계좌번호 및 신용카드번호 등 타인의 허락 없이 타인의 결제정보를 이용하여 회사의 유료서비스를 이용하는 행위
  (4) 정당한 사유 없이 당사의 영업을 방해하는 내용을 기재하는 행위
  (5) 회사가 게시한 정보를 변경하는 행위
  (6) 회사가 정한 정보 이외의 정보(컴퓨터 프로그램 등) 등을 송신 또는 게시하는 행위
  (7) 회사와 기타 제3자의 저작권 등 지적재산권을 침해하는 행위
  (8) 회사 및 기타 제3자의 명예를 손상시키거나 업무를 방해하는 행위
  (9) 외설 또는 폭력적인 메시지, 화상, 음성 기타 공공질서 미풍양속에 반하는 정보를 공개 또는 게시하는 행위
  (10) 회사의 동의 없이 영리를 목적으로 서비스를 사용하는 행위
  (11) 회사의 직원이나 서비스의 관리자를 가장하거나 사칭하여 또는 타인의 명의를 도용하여 글을 게시하거나 메일을 발송하는 행위
  (12) 서비스와 관련된 설비의 오동작이나 정보 등의 파괴 및 혼란을 유발시키는 컴퓨터 바이러스, 기타 다른 컴퓨터 코드, 파일, 프로그램 자료를 등록 또는 유포하는 행위
  (13) 회사가 제공하는 소프트웨어 등을 개작하거나 리버스 엔지니어링, 디컴파일, 디스어셈블 및 기타 일체의 가공행위를 통하여 서비스를 복제, 분해 또는 모방 기타 변형하는 행위
  (14) 자동 접속 프로그램 등을 사용하는 등 정상적인 용법과 다른 방법으로 서비스를 이용하여 회사의 서버에 부하를 일으켜 회사의 정상적인 서비스를 방해하는 행위
  (15) 다른 회원의 개인정보를 그 동의 없이 수집, 저장, 공개하는 행위
  (16) 기타 불법적이거나 회사에서 정한 규정을 위반하는 행위
④ 회사는 회원이 제1항의 행위를 하는 경우 해당 게시물 등을 삭제 또는 임시 삭제할 수 있으며 서비스의 이용을 제한하거나 일방적으로 본 계약을 해지할 수 있습니다.
⑤ 회원은 회원정보, 계정정보에 변경이 있는 경우 제7조에 따라 이를 즉시 변경하여야 하며, 더불어 비밀번호를 철저히 관리하여야 합니다. 회원의 귀책으로 말미암은 관리소홀, 부정사용 등에 의하여 발생하는 모든 결과에 대한 책임은 회원 본인이 부담하며, 회사는 이에 대한 어떠한 책임도 부담하지 않습니다.
⑥ 민법상 미성년자인 회원이 유료 서비스를 이용 할 경우 미성년자인 회원은 결제 전 법정대리인의 동의를 얻어야 하며, 만 14세 미만 아동의 경우 본 서비스를 이용할 수 없습니다.
⑦ 회원은 회사에서 공식적으로 인정한 경우를 제외하고는 서비스를 이용하여 상품을 판매하는 영업 활동을 할 수 없으며, 특히 해킹, 광고를 통한 수익, 음란사이트를 통한 상업행위, 상용소프트웨어 불법배포 등을 할 수 없습니다. 이를 위반하여 발생한 영업 활동의 결과 및 손실, 관계기관에 의한 구속 등 법적 조치 등에 관해서는 회사가 책임을 지지 않으며, 회원은 이와 같은 행위와 관련하여 회사에 대하여 손해배상 의무를 집니다.
⑧ 회원은 직접 본인의 정보로 본인인증을 하여야 하고 이를 타인으로 하여금 하게 하거나 인증번호 등의 정보를 노출하여서는 안됩니다.

제22조 (회원에 대한 통지)
① 회사가 회원에 대한 통지를 하는 경우 본 약관에 별도 규정이 없는 한 회원이 기재한 E-mail 또는 핸드폰 번호로 할 수 있습니다.
② 회사는 불특정 다수 회원에 대한 통지의 경우 서비스 게시판 등에 게시함으로써 개별 통지에 갈음할 수 있습니다.

제23조 (서비스 이용 해지)
① 회원이 이용계약을 해지하고자 하는 때에는 사이트 또는 앱을 통해 안내된 해지방법에 따라 해지를 신청할 수 있습니다.
② 회사는 등록 해지신청이 접수되면 회원이 원하는 시점에 해당 회원의 서비스 이용을 해지하여야 합니다.
③ 회원이 계약을 해지할 경우, 관련법 및 개인정보처리방침에 따라 회사가 회원정보를 보유할 수 있는 경우를 제외하고 회원의 개인정보는 해지 즉시 삭제됩니다.

제24조 (서비스 이용 제한)
① 회사는 이용제한정책에서 정하는 바에 따라 회원이 본 약관상 의무를 위반하거나 서비스의 정상적인 운영을 방해한 경우, 경고∙일시적 이용정지∙영구적 이용정지 등의 단계로 서비스 이용을 제한하거나 이용계약을 해지할 수 있습니다. 단, 회원에게 제6조 제1항의 사유가 있음이 확인되거나 회원이 서비스 이용과 관련하여 불법행위를 하거나 조장∙방조한 경우에는 즉시 영구적 이용정지 조치 또는 이용계약 해지를 할 수 있습니다.
② 회사는 회원이 계속해서 1년 이상 서비스를 이용하지 않은 경우에는, 정보통신망법에 따라 필요한 조치를 취할 수 있고, 회원정보의 보호 및 운영의 효율성을 위해 이용을 제한할 수 있습니다.
③ 본 조에 따라 서비스 이용을 제한하거나 계약을 해지하는 경우에는 회사는 제22조에 따라 회원에게 통지합니다.
④ 회원은 본조에 따른 서비스 이용정지 기타 서비스 이용과 관련된 이용제한에 대해 회사가 정한 절차에 따라 이의신청을 할 수 있으며, 회사는 회원의 이의신청이 정당하다고 판단되는 경우 즉시 서비스 이용을 재개합니다.


제5장 기타
제25조 (손해 배상)
① 회원이 본 약관의 규정을 위반함으로 인하여 회사에 손해가 발생하게 되는 경우, 본 약관을 위반한 회원은 회사에 발생하는 모든 손해를 배상하여야 합니다.
② 회원이 서비스를 이용하는 과정에서 행한 불법행위나 본 약관 위반행위로 인하여 회사가 당해 회원 이외의 제3자로부터 손해배상 청구 또는 소송을 비롯한 각종 이의제기를 받는 경우 당해 회원은 자신의 책임과 비용으로 회사를 면책 시켜야 하며, 회사가 면책되지 못한 경우 당해 회원은 그로 인하여 회사에 발생한 모든 손해를 배상하여야 합니다.

제26조 (면책사항)
① 회사는 천재지변 또는 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 관한 책임이 면책됩니다.
② 회사는 회원의 귀책사유로 인한 서비스의 이용장애에 대하여 책임을 지지 않습니다.
③ 회사는 회원이 서비스를 이용하여 기대하는 수익을 상실한 것에 대하여 책임을 지지 않으며 그 밖에 서비스를 통하여 얻은 자료로 인한 손해 등에 대하여도 책임을 지지 않습니다. 회사는 회원이 게재한 게시물(이용후기, 숙소평가 등 포함)의 정확성 등 내용에 대하여는 책임을 지지 않습니다.
④ 회사는 회원 상호간 또는 회원과 제3자 상호간에 서비스를 매개로 발생한 분쟁에 대해서는 개입할 의무가 없으며 이로 인한 손해를 배상할 책임도 없습니다.
⑤ 상품등은 판매자의 책임 하에 관리∙운영되고, 회사는 통신판매중개자로서 서비스 운영 상의 문제를 제외한 상품등의 하자∙부실 등으로 인한 책임은 판매자에게 귀속되며 회사는 어떠한 책임도 부담하지 않습니다.
⑥ 회사는 제3자가 서비스 내 화면 또는 링크된 웹사이트를 통하여 광고한 제품 또는 서비스의 내용과 품질에 대하여 감시할 의무가 없으며 그로 인한 어떠한 책임도 지지 아니합니다.
⑦ 회사는 회사 및 회사의 임직원 그리고 회사 대리인의 고의 또는 중대한 과실이 없는 한 다음 각 호의 사항으로부터 발생하는 손해에 대해 책임을 지지 아니합니다.
  (1) 회원정보 등의 허위 또는 부정확성에 기인하는 손해
  (2) 서비스에 대한 접속 및 서비스의 이용과정에서 발생하는 개인적인 손해
  (3) 서버에 대한 제3자의 모든 불법적인 접속 또는 서버의 불법적인 이용으로부터 발생하는 손해
  (4) 서버에 대한 전송 또는 서버로부터의 전송에 대한 제3자의 모든 불법적인 방해 또는 중단행위로부터 발생하는 손해
  (5) 제3자가 서비스를 이용하여 불법적으로 전송, 유포하거나 또는 전송, 유포되도록 한 모든 바이러스, 스파이웨어 및 기타 악성 프로그램으로 인한 손해
  (6) 전송된 데이터의 오류 및 생략, 누락, 파괴 등으로 발생되는 손해
  (7) 회원간의 회원 상태정보 등록 및 서비스 이용 과정에서 발생하는 명예훼손 기타 불법행위로 인한 각종 민∙형사상 책임

제27조 (분쟁 조정 및 관할법원)
본 약관과 회사와 회원간에 발생한 분쟁 등에 관하여는 대한민국 법령이 적용되며, 회사의 주소지를 관할하는 법원을 관할법원으로 합니다.

* 부 칙
1. 본 약관은 2022년 09월 01일부터 시행됩니다.
		      		</textarea>
		      	</div>
		      </div>
		    </div>
		  </div>
		  <div class="accordion-item">
		    <h2 class="accordion-header rule-header-wrap" id="flush-headingTwo">
		      <input class="form-check-input" type="checkbox" id="rule-check" name="default-check">
		      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
		        개인정보 수집이용 동의(필수)
		      </button>
		    </h2>
		    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
		      <div class="accordion-body">
		      	<div class="rule-wrap form-floating">
	      			<textarea class="form-control" id="utilization" cols="100" rows="10">
미리해 서비스 이용에 대하여 개인정보 수집이용 내용을 숙지하였으며, 이에 따라 아래와 같이 수집하는 것에 대해 동의합니다.

- 수집항목 : 이메일 주소, 비밀번호, 이름, 휴대폰번호
- 수집/이용 목적 : 예매 확인/취소/ 발권에 따른 본인확인 및 서비스 관련 고지사항 전달을 위한 의사소통 경로 확보
- 보유 및 이용기간 : 전자상거래에서의 계약, 청약철회, 대금결제, 재화 등 공급기록 5년

본 동의는 서비스의 본질적 기능 제공을 위한 개인정보 수집/이용에 대한 동의로서, 동의하지 않으실 경우 서비스 제공이 불가능합니다.
※ 법령에 따른 개인정보의 수집/이용, 계약의 이행/편의증진을 위한 개인정보 취급과 관련된 일반 사항은 서비스의 개인정보 취급방침을 따릅니다.	      				
	      			</textarea>
	      		</div>
		      </div>
		    </div>
		  </div>
		  <div class="accordion-item">
		    <h2 class="accordion-header rule-header-wrap" id="flush-headingThree">
		    <input class="form-check-input" type="checkbox" id="rule-check">
		      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
		        캠핑이지 광고성 정보수신 동의(선택)
		      </button>
		    </h2>
		    <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
		      <div class="accordion-body">
		      	<div class="rule-wrap form-floating">
	      			<textarea class="form-control" id="utilization" cols="100" rows="10">
① 분류
 - 선택정보

② 수집∙이용동의 목적
 - 개인맞춤형 상품∙서비스 혜택에 관한 정보제공, 통계∙분석∙이용내역 정보와의 결합 및 분석, 회사 또는 기타 광고주의 요청에 따른 정보∙안내(프로모션∙이벤트)전송
 
③ 항목
 - 예약내역(예약일시, 결제금액, 업체명), 디바이스 ID, 휴대폰 번호, 서비스이용기록, IP주소, 접속로그, Cookie, 광고식별자, 단말기 OS와 버전, 단말기 모델명, 브라우저 버전, 예약자 및 구매자 정보(이름, 휴대폰 번호, 생년월일, 이메일 주소), 방문자 및 탑승자 정보(이름, 휴대폰 번호, 생년월일, 영문이름, 자녀의 나이), 상담내용 및 상담에 필요한 개인정보
 
④ 보유∙이용기간
 - 동의 철회, 회원 탈퇴 요청 시 까지
				  </textarea>
      			</div>
		      </div>
		    </div>
		  </div>
		</div>
		<div class="form-check allCheck">
		  <input class="form-check-input" type="checkbox" id="all-check">
		  <label class="form-check-label label" for="flexCheckDefault">
		    전체동의
		  </label>
		</div>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <input class="btn btn-outline-primary" type="submit" value="가입">
      <input class="btn btn-outline-secondary" type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}';">
    </form:form>
  </div>
<script>
const password = document.querySelector("#password");
const msgPassword = document.querySelector("#msgPassword");
const passwordCheck = document.querySelector("#passwordCheck");
const msgPasswordCheck = document.querySelector("#msgPasswordCheck"); 

//이메일 유효성
function emailCK () {
	const email = document.querySelector("#email");
	const emailCheck = document.querySelector('#mail-Check-Btn');
	if(/^([\w\.-]+)@([\w-]+)(\.[\w-]+){1,2}$/.test(email.value)) {
		emailCheck.disabled = false;
	}
	else {
		return false;
	}
};

//가입버튼
document.userEnrollFrm.addEventListener('submit', (e) => {
	const mailCheckVal = document.querySelector('#mailCheckVal').value;
	const idCheckVal = document.querySelector('#idCheckVal').value;
	
	e.preventDefault(); // 제출방지
	if(idCheckVal == 1){
		if(password.value == passwordCheck.value){
			if(mailCheckVal == 0){
				alert('메일 인증이 필요합니다.');
				return false;
			}
			else{
				const total = $("input[name=default-check]").length
				const checked = $("input[name=default-check]:checked").length;
				if(total == checked) {
					document.userEnrollFrm.submit();
				} else {
					alert("필수 이용약관을 동의해주세요.");
					return;
				}
			}
		}
		else{
			alert('비밀번호를 다시 입력해주세요.');
		}
	}
	else{
		alert('사용할 수 없는 아이디입니다.');
	}
	
});

//인증 이메일
var code = "";

$('#mail-Check-Btn').click(function() {
	const email = $('#email').val(); // 이메일 주소값 얻어오기!
	console.log('이메일 : ' + email); // 이메일 오는지 확인
	const checkInput = $('.mail-check-input') // 인증번호 입력하는곳 
	const emailCheck = document.querySelector('#mail-Check-Btn');
	emailCheck.disabled = true;
	
	$.ajax({
		url : `${pageContext.request.contextPath}/user/userEmailCheck.do`,
		data : {
			email
		},
		success(data){
			console.log(data);
			checkInput.attr("disabled",false);
			//document.querySelector('.mail-check-input').value = data;
			code = data;
		},		
		error : console.log
	});
});

//인증번호 비교
$(".mail-check-input").blur(function(){
	const inputCode = document.querySelector('.mail-check-input').value; 
	const checkResult = document.querySelector('#mail-check-warn'); 
	const mailCheckVal = document.querySelector('#mailCheckVal').value;
	
    if(inputCode == code){
    	console.log('일치');
        checkResult.innerHTML = "인증번호가 일치합니다.";
        checkResult.classList.remove("incorrect");
        checkResult.classList.add("correct");
        document.querySelector('#mailCheckVal').value = "1";
        
    } else {
    	console.log('불일치');
        checkResult.innerHTML = "인증번호를 다시 확인해주세요."
        checkResult.classList.remove("correct");
        checkResult.classList.add("incorrect");
        return false;
    }    
});


const availableCheck = (input, result, msg) => {
	if (result === "fail") {
	  input.style.color = 'red';
	} else {
		input.style.color = 'blue';
	}
	input.innerHTML = msg;
};

function checkId(){
	let userId = document.querySelector('#userId').value;
	
	const headers = {};
	headers['${_csrf.headerName}'] = '${_csrf.token}';
	
	$.ajax({
		headers,
		url : `${pageContext.request.contextPath}/user/userIdCheck.do`,
		data : {userId},
		method : "GET",
		success(response){
			//console.log(response);
			if(response != 0 || userId.length < 4){	
				$("#msguserId").html('사용할 수 없는 아이디입니다');
				$("#msguserId").css("color", 'red');
				document.querySelector('#idCheckVal').value = "0";
			}	
			else if(userId == '' || userId.length == 0){
				document.querySelector('#msguserId').innerHTML = '';
			}
			else if(response == 0) {
				$("#msguserId").html('사용가능한 아이디입니다');
				$("#msguserId").css("color", 'blue');
				document.querySelector('#idCheckVal').value = "1";
			}
			
		},
		error : console.log
		
	});
};

function passwordCK () {
	var reg =  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
	
	if (!reg.test(password.value)) {
		availableCheck(msgPassword, "fail", "사용 불가능");
		if(password.value == ''){
			document.querySelector('#msgPassword').innerHTML = '';
		}
	} 
	else {
		availableCheck(msgPassword, "clear", "사용 가능");
	}
};

passwordCheck.addEventListener('input', (e) => {
	var reg =  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
	
	if (password.value !== passwordCheck.value) {
		availableCheck(msgPassword, "fail", "실패");
		//availableCheck(msgPasswordCheck, "fail", "다시입력하세요");
		//passwordCheck.focus();
		//passwordCheck.value ="";
	}
	else{
		availableCheck(msgPassword, "clear", "사용 가능");
		document.querySelector('#msgPasswordCheck').innerHTML = '';
	}

	if(passwordCheck.value == ''){
		document.querySelector('#msgPassword').innerHTML = '';
	}
});

// 약관 동의
document.querySelector("#all-check").addEventListener('click', (e) => {
	const checkes = document.querySelectorAll("#rule-check");
	const allCheck = e.target.checked;
	console.log(e.target.checked);
	
	checkes.forEach((check) => {
		check.checked = allCheck;
	});
});

</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>