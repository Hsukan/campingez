package com.kh.campingez.user.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.kh.campingez.assignment.model.dto.AssignmentEntity;
import com.kh.campingez.coupon.model.dto.Coupon;
import com.kh.campingez.inquire.model.dto.Inquire;
import com.kh.campingez.reservation.model.dto.Reservation;
import com.kh.campingez.trade.model.dto.Trade;
import com.kh.campingez.trade.model.dto.TradeEntity;
import com.kh.campingez.user.model.dto.MyPage;
import com.kh.campingez.user.model.dto.User;

@Mapper
public interface UserInfoDao {

	@Update(" update ez_user "
			  + " set user_name = #{userName}, password = #{password}, email = #{email}, phone = #{phone}, gender = #{gender} "
			  + " where user_id = #{userId}")
	int profileUpdate(User user);
	
	
	@Delete("Delete from ez_user where user_id = #{userId}")
	int profileDelete(User user);
	
	
	List<Inquire> selectInquireList(RowBounds rowBounds, User user);

	
	List<Reservation> selectReservationList(RowBounds rowBounds, User user);

	
	List<MyPage> selectInquireCnt(User user);


	List<Reservation> selectReservation(User user);

	
	List<Coupon> selectCoupon(User user);

	@Select("select * from assignment where user_id = #{userId} order by assign_date desc")
	List<AssignmentEntity> selectAssignList(RowBounds rowBounds, User user);

	
	List<MyPage> selectTradeCnt(User user);

	@Select("select t.*, trade_read_count read_count,trade_like_count like_count, (select category_name from category_list where category_id = t.category_id) category_name from trade t where user_id = #{userId}")
	List<Trade> selectTradeList(RowBounds rowBounds, User user);

	@Select("select count(*) from assignment where user_id = #{userId}")
	int getTotalAssignment(User user);

	@Select("select count(*) from trade where user_id = #{userId}")
	int getTotalTrade(User user);

	@Select("select count(*) from reservation where user_id = #{userId}")
	int getTotalReservation(User user);

	@Select("select t.*,trade_read_count read_count,trade_like_count like_count from trade t inner join trade_like tr on t.trade_no = tr.trade_no where tr.user_id = #{userId}")
	List<TradeEntity> selectLikeList(RowBounds rowBounds, User user);

	@Select("select count(*) from trade t inner join trade_like tr on t.trade_no = tr.trade_no where tr.user_id = #{userId}")
	int getTotalLike(User user);

	@Select("select * from reservation where res_no = #{resNo}")
	Reservation selectReservationDetail(String resNo);

	@Select("select count(*) from inquire where inq_writer = #{userId}")
	int getTotalInquire(User user);

}
