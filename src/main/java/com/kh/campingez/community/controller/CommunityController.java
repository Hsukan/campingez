package com.kh.campingez.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.campingez.alarm.model.service.AlarmService;
import com.kh.campingez.common.CampingEzUtils;
import com.kh.campingez.common.category.mode.dto.Category;
import com.kh.campingez.community.model.dto.Community;
import com.kh.campingez.community.model.dto.CommunityComment;
import com.kh.campingez.community.model.dto.CommunityLike;
import com.kh.campingez.community.model.dto.CommunityPhoto;
import com.kh.campingez.community.model.service.CommunityService;
import com.kh.campingez.trade.model.service.TradeService;
import com.kh.campingez.user.model.dto.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/community")
public class CommunityController {
   
   @Autowired
   CommunityService communityService;
   
   @Autowired
   ServletContext application;
   
   @Autowired
   TradeService tradeService;
   
   @Autowired
   AlarmService alarmService;
   
   @GetMapping("/communityListFree.do")
   public void communityListFree(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
      // 1. content??????
      Map<String, Integer> param = new HashMap<>();
      int limit = 10;
      param.put("cPage", cPage);
      param.put("limit", limit);
      List<Community> listFree = communityService.selectCommListFree(param);
      log.debug("listFree = {}", listFree);
      model.addAttribute("listFree", listFree);
      
      // 2. pagebar??????
      int totalContentFree = communityService.getTotalContentFree();
      log.debug("totalContentFree = {}", totalContentFree);
      String url = request.getRequestURI(); // /spring/board/boardList.do
      String pagebarFree = CampingEzUtils.getPagebar2(cPage, limit, totalContentFree, url);
      model.addAttribute("pagebarFree", pagebarFree);
   }
   
   @GetMapping("/communityListHoney.do")
   public void communityListHoney(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
	   // 1. content??????
	   Map<String, Integer> param = new HashMap<>();
	   int limit = 10;
	   param.put("cPage", cPage);
	   param.put("limit", limit);
	   List<Community> listHoney = communityService.selectCommListHoney(param);
	   log.debug("listHoney = {}", listHoney);
	   model.addAttribute("listHoney", listHoney);
	   
	   // 2. pagebar??????
	   int totalContentHoney = communityService.getTotalContentHoney();
	   log.debug("totalContentHoney = {}", totalContentHoney);
	   String url = request.getRequestURI(); // /spring/board/boardList.do
	   String pagebarHoney = CampingEzUtils.getPagebar2(cPage, limit, totalContentHoney, url);
	   model.addAttribute("pagebarHoney", pagebarHoney);
   }
   
   @GetMapping("/communityFind.do")
   public String communityFind(@RequestParam String searchType, @RequestParam String categoryType, @RequestParam String searchKeyword,
		   @RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
	   log.debug("categoryType = {}", categoryType);
	   log.debug("searchType = {}", searchType);
	   log.debug("searchKeyword = {}", searchKeyword);
	   
	   Map<String, Integer> param = new HashMap<>();
      int limit = 10;
      param.put("cPage", cPage);
      param.put("limit", limit);
      List<Community> list = communityService.communityFind(param, categoryType, searchType, searchKeyword);
      log.debug("list = {}", list);
      model.addAttribute("list", list);
      model.addAttribute("categoryType", categoryType);
      model.addAttribute("searchType", searchType);
      model.addAttribute("searchKeyword", searchKeyword);
      
      //?????????
      int totalContent = communityService.getFindTotalContent(categoryType, searchType, searchKeyword);
      log.debug("totalContent = {}", totalContent);
      String url = request.getRequestURI(); // /spring/board/boardList.do
      String pagebar = CampingEzUtils.getPagebar2(cPage, limit, totalContent, url);
      model.addAttribute("pagebar", pagebar);
      
      return "community/communityFindList";
   }
   
   @GetMapping("/communityView.do")
   public ModelAndView communityView(
         @RequestParam String no, Model model,
         HttpServletResponse response, HttpServletRequest request,
         RedirectAttributes redirectAttr
         ) throws Exception {
      
      // ???????????? ????????? ????????????
      SecurityContext securityContext = SecurityContextHolder.getContext();
       Authentication authentication = securityContext.getAuthentication();
       Object principal = authentication.getPrincipal();
       log.debug("prin = {}", principal);
       
       // ????????? ???????????? ??????
       Community community = communityService.selectCommByNo(no);
       model.addAttribute("community", community);
       log.debug("community = {}", community);
       // ?????? ????????? ??????????????? ????????? ??? ?????? ??????
       String userId = principal != "anonymousUser" ? ((User)principal).getUserId() : null;
       Map<String, Object> param = new HashMap<>();
       param.put("no", no);
       param.put("userId", userId);
       String reportUserId = communityService.getUserReportComm(param);
       model.addAttribute("reportUserId", reportUserId);
       
       // ????????????
       List<CommunityComment> commentlist = communityService.selectCommentList(no);
       log.debug("commentlist = {}", commentlist);
	   model.addAttribute("commentlist", commentlist);
	   
	   // ???????????? ????????? ????????????
		List<Category> categoryList = tradeService.getReportCategory();
		model.addAttribute("categoryList", categoryList);
             
       CommunityLike cl = new CommunityLike();

       // ????????? ??? ????????? ??????(????????? ?????????)
       if(principal != "anonymousUser") {
       User user = (User) principal;
       userId = user.getUserId();   
       
       model.addAttribute("user", user);
       
       cl.setLikeCommNo(no);
       cl.setLikeUserId(userId);
       
       int commLike = communityService.getCommLike(cl);
       
       model.addAttribute("heart", commLike);
       } else {
          int commLike = 0;
          model.addAttribute("heart", commLike);
       }
       
      // ????????? ??????
      ModelAndView view = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        
        // ???????????? ?????? ????????? ??????
        Cookie viewCookie = null;
 
        // ????????? ?????? ?????? 
        if (cookies != null && cookies.length > 0) 
        {
            for (int i = 0; i < cookies.length; i++)
            {
                // Cookie??? name??? cookie + reviewNo??? ???????????? ????????? viewCookie??? ????????? 
                if (cookies[i].getName().equals("cookie"+ no))
                { 
                    log.debug("?????? ????????? ????????? ??? ?????????.");
                    viewCookie = cookies[i];
                }
            }
        }
        if (community != null) {
            log.debug("System - ?????? ?????? ?????????????????? ?????????");
            
 
            // ?????? viewCookie??? null??? ?????? ????????? ???????????? ????????? ?????? ????????? ?????????.
            if (viewCookie == null) {    
                log.debug("cookie ??????");
                // ?????? ??????(??????, ???)
                Cookie newCookie = new Cookie("cookie"+ no, "|" + no + "|");  
                newCookie.setMaxAge(60 * 60 * 24);
                // ?????? ??????
                response.addCookie(newCookie);
                // ????????? ?????? ????????? ????????? ????????????
                int result = communityService.updateReadCount(no);
                log.debug("?????????????????? ?????? : " + community.getReadCount());
                
                if(result>=0) {
                    log.debug("????????? ??????");
                }else {
                    log.debug("????????? ?????? ??????");
                }
            }
            // viewCookie??? null??? ???????????? ????????? ???????????? ????????? ?????? ????????? ???????????? ??????.
            else {
                log.debug("cookie ??????");
                
                // ?????? ??? ?????????.
                String value = viewCookie.getValue();
                log.debug("?????????????????? ?????? : " + community.getReadCount());
                log.debug("cookie ??? : " + value);     
            }
            
            log.debug("result = {}", community);
            view.setViewName("community/communityView");
            return view;
        } 
        else {
            // ?????? ????????? ??????
            view.setViewName("error/communityError");
            return view;
        }
   }
        
        @GetMapping("/communityEnroll.do")
       public void communityEnroll() {
          
       }
       
       @PostMapping("/communityEnroll.do")
       public String communityEnroll(
             Community community,
             @RequestParam(name = "upFile") List<MultipartFile> upFileList,
             RedirectAttributes redirectAttr) throws IllegalStateException, IOException {
          

          for(MultipartFile upFile : upFileList) {
             // ?????????????????? ??????
             if(!upFile.isEmpty()) {
                String saveDirectory = application.getRealPath("/resources/upload/community");
                String renamedFilename = CampingEzUtils.getRenamedFilename(upFile.getOriginalFilename());
                File destFile = new File(saveDirectory, renamedFilename);
                upFile.transferTo(destFile);
                
                // DB??? ??????
                CommunityPhoto photo = new CommunityPhoto(upFile.getOriginalFilename(), renamedFilename);
                community.add(photo);
             }
             
             
          }
          log.debug("community = {}", community);

          log.debug("upFileList = {}", upFileList);

          
          int result = communityService.insertComm(community);
          
          redirectAttr.addFlashAttribute("msg", "????????? ?????? ??????");
          
          return "redirect:/community/communityListFree.do";
       }
       
       @GetMapping("/communityUpdate.do")
       public void communityUpdate(@RequestParam String no, Model model) {
          Community community = communityService.selectCommByNo(no);
          model.addAttribute("community", community);
          log.debug("community : {}", community);
       }
       
       @PostMapping("/communityUpdate.do")
       public String communityUpdate(
                Community community,
                @RequestParam(name = "upFile") List<MultipartFile> upFileList,
                @RequestParam(name = "delFile", required = false) int[] delFiles,
                RedirectAttributes redirectAttr) throws IllegalStateException, IOException{
          log.debug("community : {}", community);
          String saveDirectory = application.getRealPath("/resources/upload/community");
          int result = 0;
          // ?????? ?????? ??????
          if(delFiles != null) {
             for(int photoNo : delFiles) {
                // ????????? ????????? ?????? ??????
                CommunityPhoto photo = communityService.selectOnePhoto(photoNo);
                File delFile = new File(saveDirectory, photo.getRenamedFilename());
                boolean deleted = delFile.delete();
                
                // DB??? photo row ??????
                result = communityService.deletePhoto(photoNo);
             }
          }
          
          // ????????? ?????? ??????(?????? ??????, insert??? ???????????? ??????)
          for(MultipartFile upFile : upFileList) {
             if(!upFile.isEmpty()) {
                // ????????? ??????
                if(!upFile.isEmpty()) {
                   String renamedFilename = CampingEzUtils.getRenamedFilename(upFile.getOriginalFilename());
                   File destFile = new File(saveDirectory, renamedFilename);
                   upFile.transferTo(destFile);
                   
                   // DB??? ??????
                   CommunityPhoto photo = new CommunityPhoto(upFile.getOriginalFilename(), renamedFilename);
                   photo.setCmNo(community.getCommNo()); // fk ??????
                   community.add(photo);
             }
          }
          
       }

       // ????????? ??????
       result = communityService.updateComm(community);
       log.debug("result : {}", result);
       log.debug("community : {}", community);
       
       // ?????????
       redirectAttr.addFlashAttribute("msg", "?????? ??????");
       
       return "redirect:/community/communityView.do?no=" + community.getCommNo();
       }
        
       
       @GetMapping("/communityDelete")
       public String communityDelete(RedirectAttributes redirectattr, @RequestParam String no) {
          int result = communityService.deleteComm(no);
          
          return "redirect:/community/communityListFree.do";
       }
       
       @ResponseBody
       @PostMapping(path = "/heart", produces = "application/json")
       public int heart(HttpServletRequest request) throws Exception{
          
          // ????????? ??????
          SecurityContext securityContext = SecurityContextHolder.getContext();
           Authentication authentication = securityContext.getAuthentication();
           Object principal = authentication.getPrincipal();
           log.debug("prin = {}", principal);
           
           User user = (User) principal;
           String userId = user.getUserId();
          
           // ????????? ??????
          int heart = Integer.parseInt(request.getParameter("heart"));
          String commNo = request.getParameter("commNo");
          
          CommunityLike cl = new CommunityLike();
          cl.setLikeCommNo(commNo);
          cl.setLikeUserId(userId);
          
          if(heart >= 1) {
             communityService.deleteCommLike(cl);
             heart = 0;
          } else {
             communityService.insertCommLike(cl);
             heart = 1;
          }
          
          
          return heart;
       }
       
       @PostMapping("/commentEnroll.do")
       public String commentEnroll(CommunityComment cc, HttpServletRequest request,
					    		   @RequestParam String cContent,
					    		   @RequestParam String commNo) {
    	   log.debug("CommunityComment = {}", cc);
    	   log.debug("cContent = {}, commNo = {}", cContent, commNo);
    	   
    	   // ????????? ??????
           SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            Object principal = authentication.getPrincipal();
            log.debug("prin = {}", principal);
            
            if(principal != "anonymousUser") {
                User user = (User) principal;
                String userId = user.getUserId();
                cc.setUserId(userId);
            } 
            cc.setCommentContent(cContent);
            cc.setCommentCommNo(commNo);
            
            communityService.insertComment(cc);
            
            // ?????? ??????
            alarmService.commEnrollAlarm(cc); 
            
            return "redirect:/community/communityView.do?no=" + commNo;
       }
       
       
       
       @GetMapping("/commentDelete.do")
       public String deleteComment(RedirectAttributes redirectAttr, @RequestParam String commentCommNo, String commentNo, Model model) {
    	   model.addAttribute("commentNo", commentNo);
    	   
    	   commentCommNo = communityService.selectCommNoByCommentNo(commentNo);
    	   
    	   model.addAttribute("commentCommNo", commentCommNo);
    	   log.debug("commentNo = {}",commentNo);
    	   log.debug("commentCommNo = {}", commentCommNo);
    	   int result = communityService.deleteComment(commentNo);
    	   
    	   
    	   
    	   return "redirect:/community/communityView.do?no=" + commentCommNo;
       }
       
} 
