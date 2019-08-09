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
	
	// ####### INSERT ########
	
	public Boolean addMember(MemberVo memberVo) {
		int count = sqlSession.insert("member.insertMember", memberVo);
		return 1 == count;
		
	}

	// ####### SELECT ########
	//멤버 주소가 존재하지 않는가?
	public boolean existMemberAddress(String id) {
		String address = sqlSession.selectOne("member.existMemberAddress", id);
		System.out.println("address = "+address);
		return null==address;
	}

	public boolean getEmail(String email) {
		MemberVo memberVo = sqlSession.selectOne("member.selectByEmail", email);
		return memberVo==null;
	}


	public MemberVo getInfoByEmail(String email) {
		MemberVo memberVo = sqlSession.selectOne("member.selectByEmail", email);
		return memberVo;
	}


	public Boolean getMemberByEmailandPassword(MemberVo memberVo) {
		MemberVo member = sqlSession.selectOne("member.selectByEmailandPassword", memberVo);
		return member!=null;
	}

	// ####### DELETE ########

	public boolean deleteMember(MemberVo memberVo) {
		
		return 1 == sqlSession.delete("member.deleteByEmail", memberVo);
	}


	// ####### UPDATE ########

	public boolean updateMember(MemberVo memberVo) {
		
		return 0 < sqlSession.update("member.updateMember", memberVo);
	}

	public boolean addMemberAddress(MemberVo memberVo) {
		return 1 == sqlSession.update("member.addMemberAddress", memberVo);
		
	}


	



	
	
}
