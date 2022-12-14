package com.kh.campingez.user.controller;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.campingez.assignment.model.dto.AssignmentEntity;
import com.kh.campingez.common.CampingEzUtils;
import com.kh.campingez.coupon.model.dto.Coupon;
import com.kh.campingez.inquire.model.dto.Inquire;
import com.kh.campingez.reservation.model.dto.Reservation;
import com.kh.campingez.trade.model.dto.Trade;
import com.kh.campingez.trade.model.dto.TradeEntity;
import com.kh.campingez.user.model.dto.MyPage;
import com.kh.campingez.user.model.dto.User;
import com.kh.campingez.user.model.service.UserInfoService;
import com.kh.security.model.service.UserSecurityService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	//?????? ??????????????? jsp ??????(???????????? ?????? ?????? ?????? ??????)
	@GetMapping("/myPage.do")
	public ModelAndView myPage(@RequestParam(defaultValue = "1") int cPage, Authentication authentication, ModelAndView mav, Model model) {
		User principal = (User)authentication.getPrincipal();
		Map<String, Object> param = new HashMap<>();
		int limit = 3;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		//???????????? ????????? 1:1 ?????? ?????? ??????
		List<MyPage> inquireCnt = userInfoService.selectInquireCnt(principal);
		model.addAttribute("inquireCnt",inquireCnt);
		
		//???????????? ????????? ?????? ?????? ????????? ??????.
		List<MyPage> tradeCnt = userInfoService.selectTradeCnt(principal);
		model.addAttribute("tradeCnt",tradeCnt);
		
		//???????????? ????????? ?????? ?????? ????????????
		List<Reservation> reservationList= userInfoService.selectReservation(principal);
		model.addAttribute("reservationList",reservationList);
		
		//???????????? ????????? ???????????? ?????? ???????????????
		List<Coupon> couponList = userInfoService.selectCoupon(principal); 
		model.addAttribute("couponList",couponList);
		
		//???????????? ????????? ????????? ????????? ??????.
		
		List<AssignmentEntity> assignList = userInfoService.selectAssignList(param, principal); 
		model.addAttribute("assignList",assignList);
	
		mav.setViewName("user/myPage");
		return mav;
	}
	
	/**
	 * !!!!!!!!!!!!!!!!???????????? ??????!!!!!!!!!!!!!!!!!!!!!
	 */
	//????????? ?????? ??????????????? (???????????? ??????????????? ????????????!)
	@GetMapping("/popupAuthentication.do")
	public ModelAndView popupAuthentication(ModelAndView mav) {
		mav.setViewName("user/authentication");
		return mav;
	}
	//?????? ?????? jsp ??????
	@GetMapping("/userInfo.do")
	public ModelAndView userInfo(ModelAndView mav) {
		mav.setViewName("user/userInfo");
		return mav;
	}
	//?????? ?????? ????????? ????????? ?????? ???????????? 
	@GetMapping("/authentication.do")
	public ResponseEntity<Map<String, Object>> authentication(@ModelAttribute User user ,Authentication authentication) {
		User principal = (User)authentication.getPrincipal();
		System.out.println(user.getUserId()+"," +principal.getUserId()+"," +user.getPassword()+"," +principal.getPassword());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Map<String , Object> resultMap = new HashMap<>(); 
		if(user.getUserId().equals(principal.getUserId()) && encoder.matches(user.getPassword(), principal.getPassword())) {
			resultMap.put("msg", "success");
			
		}else {
			resultMap.put("msg", "fail");
			
		}
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(resultMap);
	}
	//?????? ?????? ??????
	@PostMapping("/profileUpdate.do")
	public String profileUpdate(@ModelAttribute User user, RedirectAttributes redirectAttr, Model model, Authentication authentication) {
		User principal = (User)authentication.getPrincipal();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(encoder.matches(user.getCPassword(), principal.getPassword())){
			String rawPassword = "";
			if(!user.getRPassword().isEmpty()) {
				rawPassword = user.getRPassword();
			}else {
				rawPassword = user.getCPassword();
			}
			log.debug("rawPassword = {}", rawPassword);
			String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
			log.debug("encodedPassword = {}", encodedPassword);
			user.setPassword(encodedPassword);		
			
			// 1. db row ??????
			int result = userInfoService.profileUpdate(user);
			if(result > 0) {
				UserDetails updatedMember = userSecurityService.loadUserByUsername(user.getUserId());
				// 2. authentication ??????
				Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
						updatedMember,
						updatedMember.getPassword(),
						updatedMember.getAuthorities()
						);
				SecurityContextHolder.getContext().setAuthentication(newAuthentication);
				redirectAttr.addFlashAttribute("msg", "??????????????? ??????????????? ??????????????????.");			
			}
		}else {
			redirectAttr.addFlashAttribute("msg", "Current Password ??? ?????? ?????? ????????????.");
		}
		log.debug("user = {}", user);
		

		return "redirect:/userInfo/userInfo.do";
	}
	//?????? ??????
	@PostMapping("/profileDelete.do")
	public String profileDelete(@ModelAttribute User user, RedirectAttributes redirectAttr, Model model) {
		log.debug("user = {}", user);
		// ?????? ??????
		int result = userInfoService.profileDelete(user);
		if(result > 0) {
			SecurityContextHolder.clearContext();
			redirectAttr.addFlashAttribute("msg", "??????????????? ?????? ???????????????.");			
		}
		return "redirect:/";
	}
	
	/**
	 * !!!!!!!!!!!!!!!!?????? ????????? ??????!!!!!!!!!!!!!!!!!!!!!
	 */
	@GetMapping("/inquireList.do")
	public ModelAndView inquireList(@RequestParam(defaultValue = "1") int cPage, Authentication authentication ,Model model, ModelAndView mav,HttpServletRequest request) {
		int limit = 6;
		Map<String, Object> param = new HashMap<>();
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<Inquire> list = userInfoService.selectInquireList(param, principal);
		log.debug("list = {}", list);
		
		//2. pagebar ??????
		int totalInquire = userInfoService.getTotalInquire(principal);
		log.debug("totalContent = {}", totalInquire);
	
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar2(cPage, limit, totalInquire, url);
		model.addAttribute("pagebar", pagebar);
		
		model.addAttribute("inquireList", list);
		model.addAttribute("prePageName", "mypage");
		model.addAttribute("cPage", cPage);
		model.addAttribute("limit", limit);
		model.addAttribute("totalContent", totalInquire);
		mav.setViewName("inquire/inquireList");
		return mav;
	}
	
	/**
	 * !!!!!!!!!!!!!!!!?????? ????????? ??????!!!!!!!!!!!!!!!!!!!!! 
	 */
	@GetMapping("/myReservation.do")
	public ModelAndView reservationCheck(@RequestParam(defaultValue = "1") int cPage, Authentication authentication ,
											Model model, ModelAndView mav, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<Reservation> list = userInfoService.selectReservationList(param, principal);
		log.debug("list = {}", list);
		
		int totalReservation =  userInfoService.getTotalReservation(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalReservation, url);
		log.debug("pagebar = {}", pagebar);
		model.addAttribute("pagebar", pagebar);
		model.addAttribute("reservationList", list);
		mav.setViewName("user/myReservation");
		return mav;
	}
	
	/**
	 * !!!!!!!!!!!!!!!!????????? ajax!!!!!!!!!!!!!!!!!!!!! 
	 */
	@PostMapping("/myReservation.do")
	public ModelAndView reservationAjax(@RequestParam(defaultValue = "1") int cPage, Authentication authentication ,
											Model model, ModelAndView mav, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<Reservation> list = userInfoService.selectReservationList(param, principal);
		log.debug("list = {}", list);
		
		int totalReservation =  userInfoService.getTotalReservation(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalReservation, url);
		log.debug("pagebar = {}", pagebar);
		

		model.addAttribute("pagebar", pagebar);
		model.addAttribute("reservationList", list);
		mav.setViewName("user/myReservationAjax");
		return mav;
	}
	
	
	/**
	 * !!!!!!!!!!!!!!!!?????? ??? ??????!!!!!!!!!!!!!!!!!!!!! 
	 */
	@GetMapping("/assignment.do")
	public ModelAndView assignList(@RequestParam(defaultValue = "1") int cPage,Authentication authentication,
									ModelAndView mav, Model model,HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<AssignmentEntity> list = userInfoService.selectAssignList(param, principal);
		log.debug("list = {}", list);
		System.out.println(list);
		int totalContent =  userInfoService.getTotalAssignment(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, url);
		log.debug("pagebar = {}", pagebar);
		System.out.println(cPage);
		model.addAttribute("pagebar", pagebar);
		
		model.addAttribute("assignList", list);
		mav.setViewName("user/myAssignment");
		return mav;
	}
	/**
	 * !!!!!!!!!!!!!!!!?????? ??? ajax ???!!!!!!!!!!!!!!!!!!!!! 
	 */
	@PostMapping("/assignment.do")
	public ResponseEntity<Map<String, Object>> assignAjax(@RequestParam(defaultValue = "1") int cPage,Authentication authentication,
									ModelAndView mav, Model model,HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<AssignmentEntity> list = userInfoService.selectAssignList(param, principal);
		log.debug("list = {}", list);
		System.out.println(list);
		int totalContent =  userInfoService.getTotalAssignment(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalContent, url);
		log.debug("pagebar = {}", pagebar);
		
		Map<String , Object> resultMap = new HashMap<>(); 
			resultMap.put("pagebar", pagebar);
			resultMap.put("assignList", list);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(resultMap);
	}
	 
	/**
	 * !!!!!!!!!!!!!!!!???????????? ????????? ???!!!!!!!!!!!!!!!!!!!!! 
	 */
	@GetMapping("/myTradeList.do")
	public ModelAndView myTradeList(@RequestParam(defaultValue = "1") int cPage ,Authentication authentication, 
										ModelAndView mav, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<Trade> result = userInfoService.selectTradeList(param, principal);
		log.debug("list = {}", result);
		
		int totalTrade =  userInfoService.getTotalTrade(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalTrade, url);
		log.debug("pagebar = {}", pagebar);
		model.addAttribute("pagebar", pagebar);
		model.addAttribute("result", result);
		mav.setViewName("user/myTradeList");
		return mav;
	}
	
	/**
	 * !!!!!!!!!!!!!!!!???????????? ajax !!!!!!!!!!!!!!!!!!!!! 
	 */
	@PostMapping("/myTradeList.do")
	public ResponseEntity<Map<String, Object>> tradeAjax(@RequestParam(defaultValue = "1") int cPage ,Authentication authentication, 
										ModelAndView mav, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<Trade> result = userInfoService.selectTradeList(param, principal);
		log.debug("list = {}", result);
		
		int totalTrade =  userInfoService.getTotalTrade(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalTrade, url);
		log.debug("pagebar = {}", pagebar);
		System.out.println(cPage + "?????????");
		System.out.println(result);
		Map<String , Object> resultMap = new HashMap<>(); 
		resultMap.put("pagebar", pagebar);
		resultMap.put("result", result);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(resultMap);
	}
	
	/**
	 * !!!!!!!!!!!!!!!!??????????????????!!!!!!!!!!!!!!!!!!!!! 
	 */
	@GetMapping("/myLikeList.do")
	public ModelAndView myLikeList(@RequestParam(defaultValue = "1") int cPage ,Authentication authentication, 
										ModelAndView mav, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<TradeEntity> result = userInfoService.selectLikeList(param, principal);
		log.debug("list = {}", result);
		
		int totalLikeCount =  userInfoService.getTotalLike(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalLikeCount, url);
		log.debug("pagebar = {}", pagebar);
		model.addAttribute("pagebar", pagebar);
		model.addAttribute("result", result);
		mav.setViewName("user/myLikeList");
		return mav;
	}

	/**
	 * !!!!!!!!!!!!!!!!?????????????????? ajax !!!!!!!!!!!!!!!!!!!!! 
	 */
	@PostMapping("/myLikeList.do")
	public ResponseEntity<Map<String, Object>> likeListAjax(@RequestParam(defaultValue = "1") int cPage ,Authentication authentication, 
										ModelAndView mav, Model model, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		int limit = 6;
		param.put("cPage", cPage);
		param.put("limit", limit);
		
		User principal = (User)authentication.getPrincipal();
		List<TradeEntity> result = userInfoService.selectLikeList(param, principal);
		log.debug("list = {}", result);
		
		int totalLikeCount =  userInfoService.getTotalLike(principal);
		String url = request.getRequestURI();
		String pagebar = CampingEzUtils.getPagebar(cPage, limit, totalLikeCount, url);
		log.debug("pagebar = {}", pagebar);
		Map<String , Object> resultMap = new HashMap<>(); 
		resultMap.put("pagebar", pagebar);
		resultMap.put("result", result);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(resultMap);
	}
	
	/**
	 * !!!!!!!!!!!!!!!!??? ???????????? ?????? !!!!!!!!!!!!!!!!!!!!! 
	 */
	@GetMapping("/resDetail.do")
	public ModelAndView resDetail(Authentication authentication, ModelAndView mav, Model model, @RequestParam String resNo) {
		
		Reservation res = userInfoService.selectReservationDetail(resNo);
		model.addAttribute("res",res);
		model.addAttribute("resNo",resNo);
		mav.setViewName("user/reservationDetail");
		return mav;		
	}
}
