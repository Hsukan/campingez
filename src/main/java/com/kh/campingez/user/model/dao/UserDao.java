package com.kh.campingez.user.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.campingez.user.model.dto.User;

@Mapper
public interface UserDao {

	int insertUser(User user);

	int insertAuthority(String userId);

	@Select("select count(*) from ez_user where user_id = #{userId}")
	int checkId(String userId);

	@Select("select * from ez_user where user_name = #{name} and phone = #{phone}")
	User findUserId(@Param("name")String name, @Param("phone")String phone);

	@Select("select * from ez_user where user_id = #{userId} and phone = #{phone} and email=#{email}")
	User findUserPassword(@Param("userId")String userId, @Param("phone")String phone, @Param("email")String email);

	@Update("update ez_user set password = #{encodedPassword} where user_id = #{userId}")
	int updatePassword(@Param("encodedPassword")String encodedPassword, @Param("userId") String userId);

	@Update("update user_coupon set coupon_usedate = current_date where user_id = #{userId} and coupon_code = #{couponCode}")
	int userUseCoupon(Map<Object, Object> map);

	@Update("update ez_user set point = point - #{point} where user_id = #{userId}")
	int userUsePoint(Map<Object, Object> map);
	
	@Insert("insert into report values(seq_report_no.nextval, #{commNo}, #{userId}, #{reportContent}, default, #{reportType}, default)")
	int insertReport(Map<String, Object> param);

	@Select("select * from ez_user where phone = #{phone}")
	User checkPhone(String phone);

	@Update("update ez_user set point = point + 100 where user_id = #{userId}")
	int giveTo100Point(String userId);

	@Update("update ez_user set point = point + #{point} where user_id = #{userId}")
	int giveToPoint(Map<String, Object> map);

}
