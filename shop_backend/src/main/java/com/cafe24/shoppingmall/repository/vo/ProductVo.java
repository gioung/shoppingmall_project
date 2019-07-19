package com.cafe24.shoppingmall.repository.vo;

import javax.validation.constraints.NotNull;

public class ProductVo {
	private long product_no;
	private String name;
	private long price;
	private String image;
	private String summary_desc;
	private String detail_desc;
	private String manufacturing_date;
	private boolean display;
	private String material;
	private String provider;
	private String manufacturer;
	private String origin;
	
	public ProductVo() {
		
	}
	
	public ProductVo(String name, long price) {
		this.name = name;
		this.price = price;
	}
	
	public long getProduct_no() {
		return product_no;
	}
	public void setProduct_no(long product_no) {
		this.product_no = product_no;
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
	public String getSummary_desc() {
		return summary_desc;
	}
	public void setSummary_desc(String summary_desc) {
		this.summary_desc = summary_desc;
	}
	public String getDetail_desc() {
		return detail_desc;
	}
	public void setDetail_desc(String detail_desc) {
		this.detail_desc = detail_desc;
	}
	public String getManufacturing_date() {
		return manufacturing_date;
	}
	public void setManufacturing_date(String manufacturing_date) {
		this.manufacturing_date = manufacturing_date;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Override
	public String toString() {
		return "ProductVo [product_no=" + product_no + ", name=" + name + ", price=" + price + ", image=" + image
				+ ", summary_desc=" + summary_desc + ", detail_desc=" + detail_desc + ", manufacturing_date="
				+ manufacturing_date + ", display=" + display + ", material=" + material + ", provider=" + provider
				+ ", manufacturer=" + manufacturer + ", origin=" + origin + "]";
	}
	
	
}
