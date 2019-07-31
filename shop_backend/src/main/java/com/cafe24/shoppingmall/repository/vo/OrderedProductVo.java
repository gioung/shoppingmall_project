package com.cafe24.shoppingmall.repository.vo;

public class OrderedProductVo {
	private long product_no;
	private long pd_detail_no;
	private long order_no;
	private long qty;
	private long pay;
	
	public OrderedProductVo( long order_no, long product_no, long pd_detail_no, long qty,long pay) {
		this.product_no = product_no;
		this.pd_detail_no = pd_detail_no;
		this.order_no = order_no;
		this.qty = qty;
		this.pay = pay;
	}
	
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

	@Override
	public String toString() {
		return "OrderedProductVo [product_no=" + product_no + ", pd_detail_no=" + pd_detail_no + ", order_no="
				+ order_no + ", qty=" + qty + ", pay=" + pay + "]";
	}
	
	
	
	
	
}
