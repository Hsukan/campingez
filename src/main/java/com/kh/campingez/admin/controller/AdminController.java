package com.kh.campingez.admin.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.campingez.admin.model.service.AdminService;
import com.kh.campingez.campzone.model.dto.CampPhoto;
import com.kh.campingez.campzone.model.dto.CampZone;
import com.kh.campingez.common.CampingEzUtils;
import com.kh.campingez.common.category.mode.dto.Category;
import com.kh.campingez.inquire.model.dto.Answer;
import com.kh.campingez.inquire.model.dto.Inquire;
import com.kh.campingez.reservation.model.dto.Reservation;
import com.kh.campingez.user.model.dto.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@Autowired
	ServletContext application;
	
	@RequestMapping("/admin.do")
	public void admin() {}
	
	@RequestMapping("/userList.do")
	public void userList(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 10;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		List<User> userList = adminService.findAllUserList(param);
		model.addAttribute("userList", userList);
		
		int totalContent = adminService.getTotalContent();
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, url);
		log.debug("pagebar = {}", pagebar);
		model.addAttribute("pagebar", pagebar);
	}

	@PostMapping("/warning")
	public ResponseEntity<?> warning(@RequestParam String userId) {
		log.debug("userId = {}", userId);
		int result = adminService.updateWarningToUser(userId);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/selectUser.do")
	public ResponseEntity<?> selectUser(@RequestParam(defaultValue = "1") int cPage, @RequestParam String selectType, @RequestParam(required = false) String selectKeyword, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 10;
		param.put("selectType", selectType);
		param.put("selectKeyword", selectKeyword);
		param.put("cPage", cPage);
		param.put("limit", limit);

		List<User> userList = adminService.selectUserByKeyword(param);
		log.debug("userList = {}", userList);		
		
		int totalContent = adminService.getTotalContentByKeyword(param);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, url);
		
		Map<String, Object> map = new HashMap<>();
		map.put("userList", userList);
		map.put("pagebar", pagebar);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(map);
	}
	
	@GetMapping("/updateUserList")
	public ResponseEntity<?> updateUserList(@RequestParam String userId) {
		User user = adminService.findUserByUserId(userId);
		log.debug("user@updateUserList = {}", user);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(user);
	}
	
	@GetMapping("/inquireList.do")
	public void findAllInquireList(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 10;
		param.put("limit", limit);
		param.put("cPage", cPage);
		
		List<Inquire> inquireList = adminService.findAllInquireList(param);
		model.addAttribute("inquireList", inquireList);
		
		int totalContent = adminService.getInquireListTotalContent();
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
		
		List<Category> categoryList = adminService.getCategoryList();
		model.addAttribute("categoryList", categoryList);
	}
	
	@PostMapping("/inquireAnswer.do")
	public String enrollAnswer(Answer answer, RedirectAttributes redirectAttr) {
		int result = adminService.enrollAnswer(answer);
		redirectAttr.addFlashAttribute("msg", "답변이 등록되었습니다.");
		
		return "redirect:/inquire/inquireDetail.do?no=" + answer.getInqNo();
	}
	
	@PostMapping("/deleteAnswer.do")
	public String deleteAnswer(Answer answer, RedirectAttributes redirectAttr) {
		int result = adminService.deleteAnswer(answer);
		
		return "redirect:/inquire/inquireDetail.do?no=" + answer.getInqNo();
	}
	
	@PostMapping("/updateAnswer.do")
	public String updateAnswer(Answer answer, RedirectAttributes redirectAttr, HttpServletRequest request) {
		int result = adminService.updateAnswer(answer);
		redirectAttr.addFlashAttribute("msg", "답변이 수정되었습니다.");
		
		return "redirect:" + request.getHeader("Referer");
	}
	
	@GetMapping("/inquireListByCategoryId.do")
	public ResponseEntity<?> findInquireListByCategoryId(@RequestParam(defaultValue = "1") int cPage, @RequestParam String categoryId, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 5;
		param.put("limit", limit);
		param.put("cPage", cPage);
		param.put("categoryId", categoryId);
		
		List<Inquire> inquireList = adminService.findInquireListByCategoryId(param);
			
		int totalContent = adminService.getInquireListTotalContentByCategoryId(categoryId);
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, uri);
		
		Map<String, Object> list = new HashMap<>();
		list.put("inquireList", inquireList);
		list.put("pagebar", pagebar);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(list);
	}
	
	@GetMapping("/reservationList.do")
	public void reservationList(@RequestParam(defaultValue = "1") int cPage, @RequestParam(defaultValue = "res_date") String searchType, HttpServletRequest request, Model model) {
		Map<String, Object> param = new HashMap<>();

		// 날짜 조회 기본 세팅 (3개월전~금일)
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date beforeThreeMon = addMonth(date, -3);
		String startDate = dateFormat.format(beforeThreeMon);
		String endDate = dateFormat.format(date);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("searchType", searchType);
		model.addAttribute("date", param);
		
		// 페이징 처리
		int limit = 5;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		List<Reservation> reservationList = adminService.findReservationList(param);
		model.addAttribute("reservationList", reservationList);
		
		int totalContent = adminService.getReservationListTotalContent(param);
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
	}
	
	@GetMapping("/reservationListBySelectType.do")
	public String reservationListBySelectType(@RequestParam(defaultValue = "1") int cPage, @RequestParam String searchType, @RequestParam String startDate, @RequestParam String endDate, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 10;
		param.put("cPage", cPage);
		param.put("limit", limit);
		param.put("searchType", searchType);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		List<Reservation> reservationList = adminService.findReservationList(param);
		model.addAttribute("reservationList", reservationList);
		
		int totalContent = adminService.getReservationListTotalContent(param);
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
		
		return "admin/reservationList";
	}
	
	@GetMapping("/campZoneList.do")
	public void campZoneList(Model model) {
		List<CampZone> campZoneList = adminService.findAllCampZoneList();
		log.debug("campZoneList = {}", campZoneList);
		model.addAttribute("campZoneList", campZoneList);
	}
	
	@GetMapping("/updateCampZone.do")
	public void updateCampZone(@RequestParam String zoneCode, Model model) {
		log.debug("zoneCode = {}", zoneCode);
		CampZone campZone = adminService.findCampZoneByZoneCode(zoneCode);
		model.addAttribute("campZone", campZone);
	}
	
	@PostMapping("/updateCampZone.do")
	public String updateCampZone(CampZone campZone, RedirectAttributes redirectAttr) {
		int result = adminService.updateCampZone(campZone);
		redirectAttr.addFlashAttribute("msg", "구역 정보가 성공적으로 수정되었습니다.");
		return "redirect:/admin/updateCampZone.do?zoneCode=" + campZone.getZoneCode();
	}
	
	@GetMapping("/insertCampZone.do")
	public void insertCampZone(Model model) {
		List<CampZone> campZoneList = adminService.findAllCampZoneList();
		model.addAttribute("campZoneList", campZoneList);
	}
	
	@PostMapping("/insertCampZone.do")
	public String insertCampZone(CampZone campZone, @RequestParam(name = "upFile") List<MultipartFile> upFiles, RedirectAttributes redirectAttr) throws IllegalStateException, IOException {
		for(MultipartFile upFile : upFiles) {
			if(!upFile.isEmpty()) {				
				String saveDirectory = application.getRealPath("/resources/upload/campPhoto");
				String renamedFilename = CampingEzUtils.getRenamedFilename(upFile.getOriginalFilename());
				File destFile = new File(saveDirectory, renamedFilename);
				upFile.transferTo(destFile);
				
				CampPhoto campPhoto = new CampPhoto(upFile.getOriginalFilename(), renamedFilename);
				campZone.campPhotoAdd(campPhoto);
			}
		}
		
		int result = adminService.insertCampZone(campZone);
		redirectAttr.addFlashAttribute("msg", "구역을 성공적으로 추가하였습니다.");
		
		return "redirect:/admin/campZoneList.do";
	}
	
	@PostMapping("/deleteCampZone.do")
	public String deleteCampZone(CampZone campZone) {
		List<CampPhoto> campPhotos = adminService.selectCampPhotoByZoneCode(campZone);
		if(!campPhotos.isEmpty()) {
			for(CampPhoto photo : campPhotos) {
				String saveDirectory = application.getRealPath("/resources/upload/campPhoto");
				File delFile = new File(saveDirectory, photo.getRenamedFilename());
				boolean deleted = delFile.delete();
				log.debug("deleted = {}", deleted);
			}
		}
		int result = adminService.deleteCampZone(campZone.getZoneCode());
		
		return "redirect:/admin/campZoneList.do";
	}
	
	private Date addMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}
}
