package com.cafe24.shppingmall.repository.vo;


public class CartVo {
	private String member_id;
	private String temp_id;
	private long seq_no;
	private String reg_date;
	private long qty;
	private long charge;
	private long sum;
	private long product_no;
	private long pd_detail_no;
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getTemp_id() {
		return temp_id;
	}
	public void setTemp_id(String temp_id) {
		this.temp_id = temp_id;
	}
	public long getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(long seq_no) {
		this.seq_no = seq_no;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public long getQty() {
		return qty;
	}
	public void setQty(long qty) {
		this.qty = qty;
	}
	public long getCharge() {
		return charge;
	}
	public void setCharge(long charge) {
		this.charge = charge;
	}
	public long getSum() {
		return sum;
	}
	public void setSum(long sum) {
		this.sum = sum;
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
	@Override
	public String toString() {
		return "CartVo [member_id=" + member_id + ", temp_id=" + temp_id + ", seq_no=" + seq_no + ", reg_date="
				+ reg_date + ", qty=" + qty + ", charge=" + charge + ", sum=" + sum + ", product_no=" + product_no
				+ ", pd_detail_no=" + pd_detail_no + "]";
	}
	
	
	
	
}



/* ---------- 개선 해야 할 것 -------------- 
 * 
 * 유효성 추가하기
 * DB 다듬기
 * */

