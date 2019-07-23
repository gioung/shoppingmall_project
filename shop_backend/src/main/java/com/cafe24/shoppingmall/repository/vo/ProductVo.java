package com.cafe24.shoppingmall.repository.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProductVo {
	//상품 번호
	private long product_no;
	//이름
	@NotNull
	private String name;
	//가격
	@NotNull
	private long price;
	//이미지
	private String image;
	//요약설명
	@NotNull
	@Length(min = 1, max = 255) //요약설명은 최대 1자이상, 255자 이하
	private String summary_desc;
	//상세설명
	@NotNull
	private String detail_desc;
	@NotNull
	private boolean display;
	@NotNull
	private String material;
	@NotNull
	private String provider;
	@NotNull
	private String manufacturer;
	@NotNull
	private String origin;
	
	public ProductVo() {
		
	}
	
	public ProductVo(String name, long price) {
		this.name = name;
		this.price = price;
	}
	
	
	public ProductVo(@NotNull String name, @NotNull long price, String image,
			@NotNull String summary_desc, @NotNull String detail_desc, @NotNull boolean display,
			@NotNull String material, @NotNull String provider, @NotNull String manufacturer, @NotNull String origin) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.summary_desc = summary_desc;
		this.detail_desc = detail_desc;
		this.display = display;
		this.material = material;
		this.provider = provider;
		this.manufacturer = manufacturer;
		this.origin = origin;
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
				+ ", summary_desc=" + summary_desc + ", detail_desc=" + detail_desc + ", display=" + display
				+ ", material=" + material + ", provider=" + provider + ", manufacturer=" + manufacturer + ", origin="
				+ origin + "]";
	}
	
	
	
}
