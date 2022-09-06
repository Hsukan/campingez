package com.kh.campingez.trade.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.campingez.trade.model.dao.TradeDao;
import com.kh.campingez.trade.model.dto.Trade;
import com.kh.campingez.trade.model.dto.TradeLike;
import com.kh.campingez.trade.model.dto.TradePhoto;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {
	
	@Autowired
	private TradeDao tradeDao;

	@Override
	public List<Trade> selectTradeList(Map<String, Integer> param) {
		int limit = param.get("limit");
		int offset = (param.get("cPage") - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return tradeDao.selectTradeList(rowBounds);
	}
	
	
//	@Override
//	public List<Trade> selectTradeListLowPrice(Map<String, Integer> param) {
//		int limit = param.get("limit");
//		int offset = (param.get("cPage") - 1) * limit;
//		RowBounds rowBounds = new RowBounds(offset, limit);
//		return tradeDao.selectTradeListLowPrice(rowBounds);
//		
//	}


//	@Override
//	public List<Trade> selectTradeListHighPrice(Map<String, Integer> param) {
//		int limit = param.get("limit");
//		int offset = (param.get("cPage") - 1) * limit;
//		RowBounds rowBounds = new RowBounds(offset, limit);
//		return tradeDao.selectTradeListHighPrice(rowBounds);
//	}


	@Override
	public int getTotalContent() {
		return tradeDao.getTotalContent();
	}


	@Override
	public Trade selectTradeByNo(String no) {
		
		Trade trade = tradeDao.selectTradeByNo(no);
		// List<TradePhoto> 조회
		List<TradePhoto> photos = tradeDao.selectPhotoListBytradeNo(no);
		trade.setPhotos(photos);
		
		return trade;
		
		
	}

	@Override
	public int insertTrade(Trade trade) {
		
		int result = tradeDao.insertTrade(trade);
		log.debug("trade#tradeNo = {}", trade.getTradeNo());
		
		// insert attachment * n
		List<TradePhoto> photos = trade.getPhotos();
		if(!photos.isEmpty()) {
			for(TradePhoto photo : photos) {
				photo.setTdNo(trade.getTradeNo());
				result = insertPhoto(photo);
			}
		}
		return result;
	}


	@Override
	public int insertPhoto(TradePhoto photo) {
		return tradeDao.insertPhoto(photo);
	}


	@Override
	public TradePhoto selectOnePhoto(int photoNo) {
		return tradeDao.selectOnePhoto(photoNo);
	}


	@Override
	public int deletePhoto(int photoNo) {
		return tradeDao.deletePhoto(photoNo);
	}


	@Override
	public int updateTrade(Trade trade) {
		int result = tradeDao.updateTrade(trade);
		
		List<TradePhoto> photos = trade.getPhotos();
		if(photos != null && !photos.isEmpty()) {
			for(TradePhoto photo : photos) {
				result = insertPhoto(photo);
			}
				
		}
		
		return result;
	}


	@Override
	public int deleteTrade(String no) {
		return tradeDao.deleteTrade(no);
	}

	
	
}

	
	

