package com.kh.campingez.user.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class UserDao {

	@Autowired
	private UserDao memberDao;
}
