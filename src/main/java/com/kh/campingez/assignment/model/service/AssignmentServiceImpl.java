package com.kh.campingez.assignment.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.campingez.assignment.model.dao.AssignmentDao;
import com.kh.campingez.assignment.model.dto.Assignment;
import com.kh.campingez.assignment.model.dto.AssignmentEntity;
import com.kh.campingez.reservation.model.dao.ReservationDao;
import com.kh.campingez.reservation.model.dto.Reservation;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDao assignmentDao;
	
	@Autowired
	ReservationDao reservationDao;
	
	@Override
	public List<Assignment> selectAssignmentList(String zoneSelect, int start, int end) {
		return assignmentDao.selectAssignmentList(zoneSelect, start, end);
	}
	
	@Override
	public int getTotalContent(String zoneSelect) {
		return assignmentDao.getTotalContent(zoneSelect);
	}
	
	@Override
	public List<Reservation> selectReservationList(String userId) {
		return assignmentDao.selectReservationList(userId);
	}
	
	@Override
	public Reservation selectResInfo(String resNo) {
		return assignmentDao.selectResInfo(resNo);
	}
	
	@Override
	public int insertAssignment(AssignmentEntity assignment) {
		return assignmentDao.insertAssignment(assignment);
	}
	
	@Override
	public Assignment assignmentDetail(String assignNo) {
		return assignmentDao.assignmentDetail(assignNo);
	}
	
	@Override
	public Assignment assignmentApplyCheck(String assignNo, String userId) {
		return assignmentDao.assignmentApplyCheck(assignNo, userId);
	}
	
	@Override
	public String selectAssignState(String assignNo) {
		log.debug("assignNo = {}", assignNo);
		String assignState = assignmentDao.selectAssignState(assignNo);
		
		return assignState;
	}
	
	@Override
	public int updateAssignStateAndTransfer(String assignNo, String assignTransfer) {
		return assignmentDao.updateAssignStateAndTransfer(assignNo, assignTransfer);
	}
	
	@Override
	public String selectOneReservation(Reservation reservation) {
		return assignmentDao.selectOneReservation(reservation);
	}

	
	@Override
	public Reservation insertAssignmentApply(Reservation reservation) {
		//1. ?????? ????????? ?????? ???????????? insert
		int result = assignmentDao.insertAssignmentApply(reservation);
		
		//2. ?????? ????????? ????????? ?????? ?????? ?????? ??????
		String resNo = reservation.getResNo();
		log.debug("resNo = {}", reservation.getResNo());
		
		return reservationDao.selectCurrReservation(resNo);
	}
	
	@Override
	public Reservation updateAssignmentApply(Reservation reservation) {
		//1. ?????? ????????? ?????? ???????????? update
		int result = assignmentDao.updateAssignmentApply(reservation);
		
		//2. ?????? ????????? ????????? ?????? ?????? ?????? ??????
		String resNo = reservation.getResNo();
		return reservationDao.selectCurrReservation(resNo);
	}
	
	@Override
	public int assignmentLimitTime() {
		int result = assignmentDao.updateAssignmetLimitTime();
		
		result = assignmentDao.deleteAssignResLimitTime();
		return result;
	}
	
	@Override
	public int deleteAssignment(String assignNo) {
		int result = 1;
		String state = selectAssignState(assignNo);
		
		if("????????????".equals(state)) {
			result = assignmentDao.deleteAssignment(assignNo);
			log.debug("result = {}", result);
		}
		else {
			result = 0;
		}
		return result;
	}
	
	
//	@Override
//	public Reservation updateAssignmentApply(String alreadyResNo) {
//		//1. ?????? ????????? ?????? ???????????? update
//		int result = assignmentDao.updateAssignmentApply(alreadyResNo);
//		
//		//2. ?????? ????????? ????????? ?????? ?????? ?????? ??????
//		String resNo = alreadyRes.getResNo();
//		log.debug("resNo = {}", alreadyRes.getResNo());
//		
//		return reservationDao.selectCurrReservation(resNo);
//	}
}
