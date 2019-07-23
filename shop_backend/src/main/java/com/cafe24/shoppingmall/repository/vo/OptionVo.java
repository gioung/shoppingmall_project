package com.cafe24.shoppingmall.repository.vo;

import java.util.List;

public class OptionVo {
	private long opt_no;
	private String opt_name;
	private List<String> opt_val;
	
	
	public long getOpt_no() {
		return opt_no;
	}
	public void setOpt_no(long opt_no) {
		this.opt_no = opt_no;
	}
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
		return "OptionVo [opt_no=" + opt_no + ", opt_name=" + opt_name + ", opt_val=" + opt_val + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
