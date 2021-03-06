package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.UserDao;
import com.cafe24.shoppingmall.repository.vo.MemberVo;


@Service
public class UserService {
	private final List<MemberVo> memberList = new ArrayList<>();
	@Autowired
	UserDao memberDao;
	@Autowired
	PasswordEncoder passwordEncoder;
	public UserService() {
		System.out.println("UserService constructor");
	}

	/* ###### INSERT ###### */
	public Boolean registerMember(MemberVo memberVo) {
		
		return memberDao.addMember(memberVo);
		
	}

	/* ###### SELECT ###### */
	public boolean existMember(MemberVo memberVo) {
		
		//이메일, 패스워드가 존재할 경우 TRUE
		return memberDao.getMemberByEmailandPassword(memberVo);
		
	}
	//모뜬 회원 가져오기
	public List<MemberVo> getAllMember(){
	
		return memberDao.getAllMember();
	}
	
	public boolean existEmail(String email) {
		return memberDao.getEmail(email);
	}
	
	// 이메일로 회원정보 가져오기
	public MemberVo getInfo(String email) {
		
		return memberDao.getInfoByEmail(email);
	}

	/* ###### UPDATE ###### */
	public boolean updateMember(MemberVo vo) {
			
		
		return memberDao.updateMember(vo);
	}

	/* ###### DELETE ###### */
	public boolean deleteMember(MemberVo memberVo) {
		 
		return memberDao.deleteMember(memberVo);
	}


	
	
	
	

	

}
