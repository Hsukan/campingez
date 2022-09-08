package com.kh.campingez.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.kh.campingez.admin.model.dto.StatsVisited;
import com.kh.campingez.campzone.model.dto.Camp;
import com.kh.campingez.campzone.model.dto.CampPhoto;
import com.kh.campingez.campzone.model.dto.CampZone;
import com.kh.campingez.common.category.mode.dto.Category;
import com.kh.campingez.inquire.model.dto.Answer;
import com.kh.campingez.inquire.model.dto.Inquire;
import com.kh.campingez.reservation.model.dto.Reservation;
import com.kh.campingez.user.model.dto.User;

@Mapper
public interface AdminDao {
	
	List<User> findAllUserList(RowBounds rowBounds);
	
	@Select("select count(*) from ez_user")
	int getTotalContent();
	
	@Update("update ez_user set yellowcard = yellowcard + 1 where user_id = #{userId}")
	int updateWarningToUser(String userId);
	
	@Select("select * from ez_user where ${selectType} like '%' || #{selectKeyword} || '%' order by enroll_date desc")
	List<User> selectUserByKeyword(RowBounds rowBounds, Map<String, Object> param);
	
	@Select("select count(*) from ez_user where ${selectType} like '%' || #{selectKeyword} || '%'")
	int getTotalContentByKeyword(Map<String, Object> param);
	
	@Select("select * from ez_user where user_id = #{userId}")
	User findUserByUserId(String userId);
	
	@Select("select i.*, (select category_name from category_list where category_id = i.category_id) category_name, nvl2((select answer_no from inquire_answer a where inq_no = i.inq_no), 1, 0) answer_status from inquire i order by answer_status, inq_date")
	List<Inquire> findAllInquireList(RowBounds rowBounds);
	
	@Insert("insert into inquire_answer values('IA' || seq_answer_no.nextval, #{inqNo}, #{answerContent}, default)")
	int enrollAnswer(Answer answer);

	@Delete("delete from inquire_answer where inq_no = #{inqNo}")
	int deleteAnswer(Answer answer);
	
	@Update("update inquire_answer set answer_content = #{answerContent} where answer_no = #{answerNo}")
	int updateAnswer(Answer answer);
	
	@Select("select count(*) from inquire")
	int getInquireListTotalContent();

	@Select("select "
				+ "i.*, "
				+ "(select category_name from category_list where category_id = i.category_id) category_name, "
				+ "nvl2((select answer_no from inquire_answer a where inq_no = i.inq_no), 1, 0) answer_status "
			+ "from "
				+ "inquire i "
			+ "where "
				+ "category_id = #{categoryId} "
			+ "order by "
				+ "answer_status, inq_date")
	List<Inquire> findInquireListByCategoryId(RowBounds rowBounds, Map<String, Object> param);

	@Select("select count(*) from inquire where category_id = #{categoryId}")
	int getInquireListTotalContentByCategoryId(String categoryId);
	
	@Select("select * from category_list where category_id like '%' || 'inq' || '%'")
	List<Category> getCategoryList();
	
	@Select("select * from reservation where to_date(${searchType}, 'YY/MM/DD') between to_date(#{startDate}, 'YY/MM/DD') and to_date(#{endDate}, 'YY/MM/DD') order by res_date desc")
	List<Reservation> findReservationList(RowBounds rowBounds, Map<String, Object> param);
	
	@Select("select count(*) from reservation where to_date(${searchType}, 'YY/MM/DD') between to_date(#{startDate}, 'YY/MM/DD') and to_date(#{endDate}, 'YY/MM/DD')")
	int getReservationListTotalContent(Map<String, Object> param);

	@Select("select * from camp_zone order by zone_code")
	List<CampZone> findAllCampZoneList();

	//@Select("select * from camp_zone where zone_code = #{zoneCode}")
	CampZone findCampZoneByZoneCode(String zoneCode);
	
	@Update("update camp_zone set zone_name = #{zoneName}, zone_info = #{zoneInfo}, zone_maximum = #{zoneMaximum}, zone_price = #{zonePrice} where zone_code = #{zoneCode}")
	int updateCampZone(CampZone campZone);
	
	@Insert("insert into camp_zone values(#{zoneCode}, #{zoneName}, #{zoneInfo}, #{zoneMaximum}, #{zonePrice})")
	int insertCampZone(CampZone campZone);
	
	@Insert("insert into camp_photo values(seq_camp_photo_no.nextval, #{zoneCode}, #{originalFilename}, #{renamedFilename})")
	int insertCampPhoto(CampPhoto photo);
	
	@Delete("delete from camp_zone where zone_code = #{zoneCode}")
	int deleteCampZone(String zoneCode);
	
	@Select("select * from camp_photo where zone_code = #{zoneCode}")
	List<CampPhoto> selectCampPhotoByZoneCode(CampZone campZone);
	
	@Select("select * from camp_photo where zone_photo_no = #{photoNo}")
	CampPhoto findCampPhotoByPhotoNo(int photoNo);
	
	@Delete("delete from camp_photo where zone_photo_no = #{photoNo}")
	int deleteCampPhotoByPhotoNo(int photoNo);
	
	@Select("select * from camp order by camp_id")
	List<Camp> findAllCampList();
	
	@Insert("insert into stats_daily_visit values(#{userId}, default)")
	int insertDailyVisit(String userId);

	@Select("select"
			+ " count(*) visit_date_count,"
			+ " to_char(visit_date, 'YYYY-MM-DD') visit_date "
			+ "from"
			+ " stats_daily_visit "
			+ "where"
			+ " extract(year from visit_date) = #{year}"
			+ " and"
			+ " extract(month from visit_date) = #{month} "
			+ "group by"
			+ " to_char(visit_date, 'YYYY-MM-DD') "
			+ "order by"
			+ " visit_date")
	List<StatsVisited> statsVisitedChartByDate(Map<String, Object> param);
	
	@Select("select count(*) visit_date_count from stats_daily_visit where extract(year from visit_date) = #{year} and extract(month from visit_date) = #{month}")
	int statsVisitedTotalCountByDate(Map<String, Object> param);
	
	@Select("select count(*) visite_date_count from stats_daily_visit")
	int statsVisitedTotalCount();

}
