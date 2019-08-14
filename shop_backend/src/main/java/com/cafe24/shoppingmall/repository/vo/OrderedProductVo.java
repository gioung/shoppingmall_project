package com.cafe24.shoppingmall.repository.vo;

public class OrderedProductVo {
	private long product_no;
	private long pd_detail_no;
	private long order_no;
	private long qty;
	private long pay;
	
	//product와 pd_detail과의 join을위해 필요한 property
	private String product_name;
	private String option;
	
	public long getProduct_no() {
		return product_no;
	}
	public void setProduct_no(long product_no) {
		this.product_no = product_no;
	}
	public long getPd_detail_no() {
		return pd_detail_no;
	}
	public void setPd_detail_no(long pd_detail_no) {
		this.pd_detail_no = pd_detail_no;
	}
	public long getOrder_no() {
		return order_no;
	}
	public void setOrder_no(long order_no) {
		this.order_no = order_no;
	}
	public long getQty() {
		return qty;
	}
	public void setQty(long qty) {
		this.qty = qty;
	}
	public long getPay() {
		return pay;
	}
	public void setPay(long pay) {
		this.pay = pay;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	@Override
	public String toString() {
		return "OrderedProductVo [product_no=" + product_no + ", pd_detail_no=" + pd_detail_no + ", order_no="
				+ order_no + ", qty=" + qty + ", pay=" + pay + ", product_name=" + product_name + ", option=" + option
				+ "]";
	}
	
	
	
	
	
}
