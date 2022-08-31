package com.kh.campingez.trade.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.campingez.trade.model.dao.TradeDao;
import com.kh.campingez.trade.model.dto.Trade;
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
	
	
	@Override
	public int getTotalContent() {
		return tradeDao.getTotalContent();
	}


	@Override
	public Trade selectTradeByNo(int no) {
		return tradeDao.selectTradeByNo(no);
	}

	@Override
	public int insertTrade(Trade trade) {
		
		int result = tradeDao.insertTrade(trade);
		log.debug("board#no = {}", trade.getTradeNo());
		
		// insert attachment * n
		List<TradePhoto> photos = trade.getPhotos();
		if(!photos.isEmpty()) {
			for(TradePhoto photo : photos) {
				photo.setTdNo(trade.getTradeNo());
				result = tradeDao.insertPhoto(photo);
			}
		}
		return result;
	}


	
	
}

	
	

