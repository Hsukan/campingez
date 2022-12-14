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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.kh.campingez.user.model.service.UserService;

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
	
	@Autowired
	UserService userService;
	
	@GetMapping("/reviewList.do")
	public void reviewList(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		List<Review> reviewList = reviewService.findAllReviewList(param);
		model.addAttribute("reviewList", reviewList);
		log.debug("reviewList = {}", reviewList);
		
		int totalContent = reviewService.getTotalContentByAllReviewList(param);
		String uri = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar2(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
		
		List<CampZone> campZoneList = adminService.findAllCampZoneList();
		model.addAttribute("campZoneList", campZoneList);
	}
	
	@GetMapping("/reviewListBySearchType.do")
	public String reviewList(@RequestParam(defaultValue = "1") int cPage, @RequestParam String searchType, @RequestParam(required = false) String campZoneType, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 4;
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
		String pagebar = CampingEzUtils.getPagebar2(cPage, limit, totalContent, uri);
		model.addAttribute("pagebar", pagebar);
		log.debug("pagebar = {}", pagebar);
		
		List<CampZone> campZoneList = adminService.findAllCampZoneList();
		model.addAttribute("campZoneList", campZoneList);
		
		return "review/reviewList";
	}
	
	@GetMapping("/reviewDetail.do")
	public ResponseEntity<?> reviewDetail(@RequestParam int revId) {
		Review review = reviewService.findOneReviewById(revId);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(review);
	}
	
	@GetMapping("/reviewForm.do")
	public ModelAndView reviewForm(Authentication authentication, ModelAndView mav, Model model, @RequestParam String resNo) {
		
		ReviewEntity review = reviewService.selectReview(resNo);
		model.addAttribute("review",review);
		model.addAttribute("resNo",resNo);
		if(review == null) {
			mav.setViewName("review/reviewInsertForm");
		}else {
			ReviewPhoto reviewPhoto = reviewService.selectReviewPhoto(review.getRevId());
			model.addAttribute("reviewPhoto",reviewPhoto);								
			mav.setViewName("review/reviewForm");
		}
		return mav;		
	}
	
	@PostMapping("/insertReview.do")
	public String boardEnroll(
			Review review, @RequestParam String userId,
			@RequestParam(name = "upFile") List<MultipartFile> upFileList, 
			RedirectAttributes redirectAttr) 
					throws IllegalStateException, IOException {
		
		System.out.println("asdasd");
		for(MultipartFile upFile : upFileList) {			

			if(!upFile.isEmpty()) {				
				// a. ?????????????????? ??????
				String saveDirectory = application.getRealPath("/resources/upload/review");
				System.out.println(saveDirectory);
				String renamedFilename = CampingEzUtils.getRenamedFilename(upFile.getOriginalFilename()); // 20220816_193012345_123.txt
				File destFile = new File(saveDirectory, renamedFilename);
				upFile.transferTo(destFile); // ??????????????? ????????? ??????
				
				// b. DB????????? ?????? Attachment?????? ??????
				ReviewPhoto photo = new ReviewPhoto(upFile.getOriginalFilename(), renamedFilename);
				review.addReviewPhoto(photo);
			}
		}
		
		log.debug("board = {}", review);
		
		// db??????
		int result = reviewService.insertReview(review);
		result = userService.giveTo100Point(userId);
		redirectAttr.addFlashAttribute("msg", "????????? ??????????????? ??????????????????.");
		
		return "redirect:/userInfo/myReservation.do";
	}
	
	//????????????
	@PostMapping("/updateReview.do")
	public String reviewUpdate(
			Review review, 
			@RequestParam(name = "upFile") List<MultipartFile> upFileList,
			RedirectAttributes redirectAttr) 
				throws IllegalStateException, IOException {
		String saveDirectory = application.getRealPath("/resources/upload/review");
		int result = 0;
	
						
						
		// 2. ??????????????? ??????(????????? ??????, DB insert??? Attachment????????????)
		for(MultipartFile upFile : upFileList) {
			if(!upFile.isEmpty()) {
				// 1. ???????????? ?????? (????????? ????????? ????????????, DB??? attachment row??????)
				// ????????? ????????? ????????????
				ReviewPhoto reviewPhoto = reviewService.selectReviewPhoto(review.getRevId());
				if(reviewPhoto == null) {
					
				}else {
					File delFile = new File(saveDirectory, reviewPhoto.getRevRenamedFilename());
					boolean deleted = delFile.delete();
					log.debug("{} ?????? ?????? : {}", reviewPhoto.getRevRenamedFilename(), deleted);
					// DB??? attachment row??????
					result = reviewService.deleteAttachment(reviewPhoto);				
				}
		
				// a. ?????????????????? ??????
				String renamedFilename = CampingEzUtils.getRenamedFilename(upFile.getOriginalFilename()); // 20220816_193012345_123.txt
				File destFile = new File(saveDirectory, renamedFilename);
				System.out.println(destFile);
				System.out.println(renamedFilename);
				upFile.transferTo(destFile); // ??????????????? ????????? ??????
				
				// b. DB????????? ?????? Attachment?????? ??????
				ReviewPhoto attach = new ReviewPhoto(upFile.getOriginalFilename(), renamedFilename);
				attach.setRevId(review.getRevId()); // fk boardNo ??????
				review.addReviewPhoto(attach);
			}
		}
						
		// 3. ????????? ??????
		result = reviewService.updateReview(review);				
		
		// 4. ????????? ????????? 
		redirectAttr.addFlashAttribute("msg", "????????? ??????????????? ??????????????????.");
						
						
		return "redirect:/review/reviewForm.do?resNo=" + review.getResNo();
	} 
	@PostMapping("/deleteReviewPhoto.do")
	public String deleteAnswer(@RequestParam(name = "revId") int revId,@RequestParam(name = "resNo") String resNo ,RedirectAttributes redirectAttr) {
		// 1. ???????????? ?????? (????????? ????????? ????????????, DB??? attachment row??????)
		// ????????? ????????? ????????????
		String saveDirectory = application.getRealPath("/resources/upload/review");
		ReviewPhoto reviewPhoto = reviewService.selectReviewPhoto(revId);
		if(reviewPhoto == null) {
			
		}else {
			File delFile = new File(saveDirectory, reviewPhoto.getRevRenamedFilename());
			boolean deleted = delFile.delete();
			log.debug("{} ?????? ?????? : {}", reviewPhoto.getRevRenamedFilename(), deleted);
			// DB??? attachment row??????
			reviewService.deleteAttachment(reviewPhoto);				
		}
		return "redirect:/review/reviewForm.do?resNo=" + resNo;
	}
	//?????? ??????
	@RequestMapping("/deleteReview.do")
	public String deleteReview(@RequestParam (name = "resNo") String resNo, RedirectAttributes redirectAttr, HttpServletRequest request) {
		int result = reviewService.deleteReview(resNo);
		redirectAttr.addFlashAttribute("msg", "?????? ?????? ??????!");
		
		return "redirect:" + (request.getHeader("Referer").indexOf("reviewList") > -1 ? request.getHeader("Referer") : "/userInfo/myReservation.do");
	}
	
	
}
