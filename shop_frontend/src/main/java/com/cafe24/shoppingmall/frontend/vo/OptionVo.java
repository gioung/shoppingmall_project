package com.cafe24.shoppingmall.frontend.vo;

import java.util.List;

public class OptionVo {
	private String opt_name;
	private List<String> opt_val;
	
	public String getOpt_name() {
		return opt_name;
	}
	public void setOpt_name(String opt_name) {
		this.opt_name = opt_name;
	}
	public List<String> getOpt_val() {
		return opt_val;
	}
	public void setOpt_val(List<String> opt_val) {
		this.opt_val = opt_val;
	}
	@Override
	public String toString() {
		return "OptionVo [opt_name=" + opt_name + ", opt_val=" + opt_val + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
