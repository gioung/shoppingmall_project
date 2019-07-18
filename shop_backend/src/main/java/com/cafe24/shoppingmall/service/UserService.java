package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.UserDao;
import com.cafe24.shppingmall.repository.vo.MemberVo;


@Service
public class UserService {
	private final List<MemberVo> memberList = new ArrayList<>();
	@Autowired
	UserDao memberDao;
	
	public UserService() {
		System.out.println("UserService constructor");
	}

	public Boolean existEmail(String email) {
		return memberDao.getEmail(email);
	}

	public boolean registerMember(MemberVo memberVo) {
		
		return memberDao.addMember(memberVo);
		
	}

	public boolean existMember(String email, String password) {
		
		//이메일, 패스워드가 존재할 경우 TRUE
		for(MemberVo vo : memberList) {
			//존재하지 않으면 true
			if(vo.getEmail().equals(email)) {
				if(vo.getPassword().equals(password))
						return true;
				else
						return false;
			}
					
				
		}
		
		return false;
	}

	public boolean updateMember(MemberVo vo) {
			
		for(int i=0; i<memberList.size();i++)
			//회원정보를 이메일 일치로 판단
			if(memberList.get(i).getEmail().equals(vo.getEmail())) {
				memberList.set(i, vo);
				return true;
			}
		return false;
	}

	public boolean deleteMember(String email) {
		for(int i=0; i<memberList.size();i++)
			//해당 회원을 삭제
			if(memberList.get(i).getEmail().equals(email)){
				memberList.remove(i);
				return true;
			}
		return false;
	}

	
	
	
	

	

}
