package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;

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

	public List<String> addOptionValList(List<OptionVo> optionList) {
		//값리스트 
		List<String> optionValList = new ArrayList<>();
		
		OptionVo optionVo1 = optionList.get(0);
		OptionVo optionVo2 = optionList.get(1);
		
		List<String> optionList1 = optionVo1.getOpt_val();
		List<String> optionList2 = optionVo2.getOpt_val();
		// 옵션 값 리스트 생성
		for(int i=0; i<optionList1.size(); i++) {
			for(int j=0; j<optionList2.size(); j++) {
					optionValList.add(optionList1.get(i).concat(optionList2.get(j)));
				}
			}
		
		return optionValList;
	}
}
