package com.cafe24.shoppingmall.frontend.vo;

import javax.validation.constraints.NotNull;

public class ProductDetailVo {
	private long pd_detail_no;
	private long product_no;
	@NotNull(message = "반드시 값이 있어야 합니다.")
	private String option;
	@NotNull(message = "반드시 값이 있어야 합니다.")
	private long inventory;
	
	public ProductDetailVo() {
		
	}
	
	
	
	public ProductDetailVo(long pd_detail_no, long product_no) {
		super();
		this.pd_detail_no = pd_detail_no;
		this.product_no = product_no;
	}



	public ProductDetailVo(String option, long inventory) {
		this.option = option;
		this.inventory = inventory;
	}
	
	
	
	public ProductDetailVo(long pd_detail_no, long product_no, @NotNull String option, @NotNull long inventory) {
		this.pd_detail_no = pd_detail_no;
		this.product_no = product_no;
		this.option = option;
		this.inventory = inventory;
	}

	public long getPd_detail_no() {
		return pd_detail_no;
	}
	public void setPd_detail_no(long pd_detail_no) {
		this.pd_detail_no = pd_detail_no;
	}
	public long getProduct_no() {
		return product_no;
	}
	public void setProduct_no(long product_no) {
		this.product_no = product_no;
	}
	
	public long getInventory() {
		return inventory;
	}
	public void setInventory(long inventory) {
		this.inventory = inventory;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "ProductDetailVo [pd_detail_no=" + pd_detail_no + ", product_no=" + product_no + ", option=" + option
				+ ", inventory=" + inventory + "]";
	}
	
	
	
	
	

	
	
	
	
	
}
