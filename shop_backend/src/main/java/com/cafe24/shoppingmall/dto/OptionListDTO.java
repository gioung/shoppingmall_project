package com.cafe24.shoppingmall.dto;

import java.util.List;

import com.cafe24.shoppingmall.repository.vo.OptionVo;

public class OptionListDTO {
	
	private List<OptionVo> optionList;

	
	
	
	public OptionListDTO(List<OptionVo> optionList) {
		super();
		this.optionList = optionList;
	}

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
