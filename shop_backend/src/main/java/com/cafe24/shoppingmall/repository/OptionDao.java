package com.cafe24.shoppingmall.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.OptionVo;

@Repository
public class OptionDao {
	@Autowired
	SqlSession sqlSession;

	public boolean addOption(OptionVo optionVo) {
		return 1 == sqlSession.insert("option.addOption", optionVo);
		
	}

	public boolean isExistThisOption(OptionVo optionVo) {
		
		return null != sqlSession.selectOne("option.isExistThisOption", optionVo);
	}
}
