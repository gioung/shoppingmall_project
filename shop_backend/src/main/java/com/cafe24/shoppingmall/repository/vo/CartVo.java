package com.cafe24.shoppingmall.repository.vo;
import javax.validation.constraints.NotNull;

public class CartVo {

	private String id;

	private long seq_no;
	@NotNull
	private long qty;
	
	private String reg_date;
	@NotNull
	private long pd_detail_no;
	@NotNull
	private long product_no;
	@NotNull
	private boolean ismember;
	private String tempId;
	
	//select시 product와 join해서 가져오는 데이터들
	private String name;
	private long price;
	private String image;
	private String option;
	
	public CartVo() {
		
	}
	
	public CartVo(@NotNull long qty, @NotNull long pd_detail_no,
			@NotNull long product_no, @NotNull boolean ismember) {
		
		this.qty = qty;
		this.pd_detail_no = pd_detail_no;
		this.product_no = product_no;
		this.ismember = ismember;
	}
	
	
	
	
	public CartVo(String id, long seq_no, @NotNull long qty, String reg_date, @NotNull long pd_detail_no,
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




	public String getReg_date() {
		return reg_date;
	}




	public void setReg_date(String reg_date) {
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
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "CartVo [id=" + id + ", seq_no=" + seq_no + ", qty=" + qty + ", reg_date=" + reg_date + ", pd_detail_no="
				+ pd_detail_no + ", product_no=" + product_no + ", ismember=" + ismember + ", tempId=" + tempId
				+ ", name=" + name + ", price=" + price + ", image=" + image + ", option=" + option + "]";
	}
	
	
	
}


