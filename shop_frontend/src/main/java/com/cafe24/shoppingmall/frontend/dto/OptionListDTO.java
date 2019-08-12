package com.cafe24.shoppingmall.frontend.dto;

import java.util.List;

import com.cafe24.shoppingmall.frontend.vo.OptionVo;

public class OptionListDTO {
	
	private List<OptionVo> optionList;

	
	
	public List<OptionVo> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<OptionVo> optionList) {
		this.optionList = optionList;
	}

	@Override
	public String toString() {
		return "OptionListDTO [optionList=" + optionList + "]";
	}
	
	
}
