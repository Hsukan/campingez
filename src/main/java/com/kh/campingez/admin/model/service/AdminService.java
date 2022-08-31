package com.kh.campingez.admin.model.service;

import java.util.List;
import java.util.Map;

import com.kh.campingez.user.model.dto.User;

public interface AdminService {

	List<User> findAllUserList(Map<String, Object> param);

	int getTotalContent();

	int updateWarningToUser(String userId);

	List<User> selectUserByKeyword(Map<String, Object> param);

}
