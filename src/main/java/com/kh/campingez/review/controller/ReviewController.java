package com.kh.campingez.review.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kh.campingez.admin.model.service.AdminService;
import com.kh.campingez.campzone.model.dto.CampZone;
import com.kh.campingez.common.CampingEzUtils;
import com.kh.campingez.review.model.dto.Review;
import com.kh.campingez.review.model.dto.ReviewEntity;
import com.kh.campingez.review.model.dto.ReviewPhoto;
import com.kh.campingez.review.model.service.ReviewService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/review")
@Slf4j
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	ServletContext application;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/reviewList.do")
	public void reviewList(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 5;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		List<Review> reviewList = reviewService.findAllReviewList(param);
		model.addAttribute("reviewList", reviewList);
		log.debug("reviewList = {}", reviewList);
		
		int totalContent = reviewService.getTotalContentByAllReviewList(param);
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
		
		List<CampZone> campZoneList = adminService.findAllCampZoneList();
		model.addAttribute("campZoneList", campZoneList);
	}
	
	@GetMapping("/reviewListBySearchType.do")
	public String reviewList(@RequestParam(defaultValue = "1") int cPage, @RequestParam String searchType, @RequestParam(required = false) String campZoneType, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 5;
		param.put("cPage", cPage);
		param.put("limit", limit);
		param.put("campZoneType", campZoneType);
		param.put("searchType", searchType);
		
		List<Review> reviewList = new ArrayList<>();
		int totalContent = 0;
		
		if(searchType.equals("rev_photo_no")) {
			reviewList = reviewService.findReviewListContainsPhoto(param);
			totalContent = reviewService.getTotalContentAllReviewListContainsPhoto(param);
		} else {
			reviewList = reviewService.findReviewListBySearchType(param);
			totalContent = reviewService.getTotalContentByAllReviewList(param);
		}
		model.addAttribute("reviewList", reviewList);
		log.debug("reviewList = {}", reviewList);
		log.debug("totalContent = {}", totalContent);
		
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
		log.debug("pagebar = {}", pagebar);
		
		List<CampZone> campZoneList = adminService.findAllCampZoneList();
		model.addAttribute("campZoneList", campZoneList);
		
		return "review/reviewList";
	}
	
	@GetMapping("/reviewDetail.do")
	public void reviewDetail(@RequestParam int revId, Model model) {
		Review review = reviewService.findOneReviewById(revId);
		model.addAttribute("review", review);
	}
	@GetMapping("/reviewForm.do")
	public ModelAndView reviewForm(Authentication authentication, ModelAndView mav, Model model, @RequestParam String resNo) {
		List<ReviewEntity> review = reviewService.selectReview(resNo);
		List<ReviewPhoto> reviewPhoto = reviewService.selectReviewPhoto(review.get(0).getRevId());
		model.addAttribute("review",review);
		model.addAttribute("reviewPhoto",reviewPhoto);		
		model.addAttribute("resNo",resNo);
		mav.setViewName("review/reviewForm");
		
		return mav;
	}
	@PostMapping("/insertReview.do")
	public String boardEnroll(
			Review review, 
			@RequestParam(name = "upFile") List<MultipartFile> upFileList, 
			RedirectAttributes redirectAttr) 
					throws IllegalStateException, IOException {
		
		
		for(MultipartFile upFile : upFileList) {			

			if(!upFile.isEmpty()) {				
				// a. 서버컴퓨터에 저장
				String saveDirectory = application.getRealPath("/resources/upload/review");
				System.out.println(saveDirectory);
				String renamedFilename = CampingEzUtils.getRenamedFilename(upFile.getOriginalFilename()); // 20220816_193012345_123.txt
				File destFile = new File(saveDirectory, renamedFilename);
				upFile.transferTo(destFile); // 해당경로에 파일을 저장
				
				// b. DB저장을 위해 Attachment객체 생성
				ReviewPhoto photo = new ReviewPhoto(upFile.getOriginalFilename(), renamedFilename);
				review.addReviewPhoto(photo);
			}
		}
		
		log.debug("board = {}", review);
		
		// db저장
		int result = reviewService.insertReview(review);
		
		redirectAttr.addFlashAttribute("msg", "리뷰를 성공적으로 작성했습니다.");
		
		return "redirect:/userInfo/myReservation.do";
	}
}