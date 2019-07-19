package com.cafe24.shoppingmall.repository;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.MemberVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	public UserDao() {
		System.out.println("UserDao Constructor");
	}
	

	
	public boolean addMember(MemberVo memberVo) {
		int count = sqlSession.insert("member.insertMember", memberVo);
		return 1 == count;
		
	}



	public Boolean getEmail(String email) {
		MemberVo memberVo = sqlSession.selectOne("member.selectByEmail", email);
		return memberVo!=null;
	}



	public Boolean getMemberByEmailandPassword(MemberVo memberVo) {
		MemberVo member = sqlSession.selectOne("member.selectByEmailandPassword", memberVo);
		return member!=null;
	}



	public boolean deleteMember(String email) {
		
		return 1 == sqlSession.delete("member.deleteByEmail",email);
	}



	public boolean updateMember(MemberVo memberVo) {
		
		return 0 < sqlSession.update("member.updateMember", memberVo);
	}
	
}
