package com.cafe24.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.OptionDao;
import com.cafe24.shoppingmall.repository.vo.OptionVo;

@Service
public class OptionService {
	@Autowired
	OptionDao optionDao;

	public boolean addOption(OptionVo optionVo) {
		//해당 옵션이 없으면
		if(!optionDao.isExistThisOption(optionVo))
			// 옵션 추가
			return optionDao.addOption(optionVo);
		else
			//그렇지 않으면 false 반환
			return false;
	}
}
