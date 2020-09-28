package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserParam;
import com.koreait.matzip.user.model.UserVO;

@Mapper
public interface UserMapper {
	public int insUser(UserVO p);
	public UserDMI selUser(UserParam p);
	
	public int insFavorite(UserParam param);
	public int delFavorite(UserParam param);
}
 