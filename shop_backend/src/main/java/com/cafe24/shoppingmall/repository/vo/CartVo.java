package com.cafe24.shoppingmall.repository.vo;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CartVo {

	private String id;

	private long seq_no;
	@NotNull
	private long qty;
	
	private Date reg_date;
	@NotNull
	private long pd_detail_no;
	@NotNull
	private long product_no;
	@NotNull
	private boolean ismember;
	private String tempId;
	
	public CartVo() {
		
	}
	
	public CartVo(@NotBlank long qty, @NotBlank long pd_detail_no,
			@NotBlank long product_no, @NotBlank boolean ismember) {
		
		this.qty = qty;
		this.pd_detail_no = pd_detail_no;
		this.product_no = product_no;
		this.ismember = ismember;
	}
	
	
	
	
	public CartVo(String id, long seq_no, @NotNull long qty, Date reg_date, @NotNull long pd_detail_no,
			@NotNull long product_no, @NotNull boolean ismember) {
		super();
		this.id = id;
		this.seq_no = seq_no;
		this.qty = qty;
		this.reg_date = reg_date;
		this.pd_detail_no = pd_detail_no;
		this.product_no = product_no;
		this.ismember = ismember;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public long getSeq_no() {
		return seq_no;
	}




	public void setSeq_no(long seq_no) {
		this.seq_no = seq_no;
	}




	public long getQty() {
		return qty;
	}




	public void setQty(long qty) {
		this.qty = qty;
	}




	public Date getReg_date() {
		return reg_date;
	}




	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
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
	public boolean isIsmember() {
		return ismember;
	}
	public void setIsmember(boolean ismember) {
		this.ismember = ismember;
	}
	
	
	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	@Override
	public String toString() {
		return "CartVo [id=" + id + ", seq_no=" + seq_no + ", qty=" + qty + ", reg_date=" + reg_date + ", pd_detail_no="
				+ pd_detail_no + ", product_no=" + product_no + ", ismember=" + ismember + "]";
	}
	
	
	
}


