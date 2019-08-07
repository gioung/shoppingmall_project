package com.cafe24.shoppingmall.frontend.repository;

import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.frontend.vo.MemberVo;


@Repository
public class UserDao {

//	@Autowired
//	private SqlSession sqlSession;
	
	public MemberVo get(String email) {
		return null;
	}
	
	public MemberVo get(Long no){
		return null;
	}
	
	public MemberVo get(String email, String password) {
		return null;
	}	
	
	public Boolean insert(MemberVo vo) {
		return false;
	}
	
	public int update( MemberVo memberVo ) {
		return 0;
	}	
}