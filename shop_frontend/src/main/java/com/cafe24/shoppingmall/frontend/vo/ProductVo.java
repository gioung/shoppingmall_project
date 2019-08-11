package com.cafe24.shoppingmall.frontend.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProductVo {
	//상품 번호
	private long product_no;
	//이름
	@NotNull(message = "반드시 값이 있어야 합니다.")
	private String name;
	//가격
	@NotNull(message = "반드시 값이 있어야 합니다.")
	private long price;
	//이미지
	private String image;
	//요약설명
	//@NotNull(message = "반드시 값이 있어야 합니다.")
	//@Length(min = 1, max = 255, message = "요약설명은 최대 1자이상, 255자 이하") //요약설명은 최대 1자이상, 255자 이하
	private String summary_desc;
	//상세설명
	//@NotNull(message = "반드시 값이 있어야 합니다.")
	private String detail_desc;
	@NotNull(message = "반드시 값이 있어야 합니다.")
	private boolean display;
	private String material;
	private String provider;
	private String manufacturer;
	@NotNull(message = "반드시 값이 있어야 합니다.")
	private String manufacturing_date;
	private String origin;
	private long main_no;
	private long sub_no;
	
	private String category_name;
	
	public ProductVo() {
		
	}
	
	public ProductVo(String name, long price) {
		this.name = name;
		this.price = price;
	}
	
	
	public ProductVo(long product_no, @NotNull String name, @NotNull long price, String image,
			@NotNull String summary_desc, @NotNull String detail_desc, @NotNull boolean display,
			@NotNull String material, @NotNull String provider, @NotNull String manufacturer, @NotNull String origin,
			@NotNull long main_no, long sub_no) {
		this.product_no = product_no;
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
		this.main_no = main_no;
		this.sub_no = sub_no;
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
	

	public long getMain_no() {
		return main_no;
	}

	public void setMain_no(long main_no) {
		this.main_no = main_no;
	}

	public long getSub_no() {
		return sub_no;
	}

	public void setSub_no(long sub_no) {
		this.sub_no = sub_no;
	}

	public String getManufacturing_date() {
		return manufacturing_date;
	}

	public void setManufacturing_date(String manufacturing_date) {
		this.manufacturing_date = manufacturing_date;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	@Override
	public String toString() {
		return "ProductVo [product_no=" + product_no + ", name=" + name + ", price=" + price + ", image=" + image
				+ ", summary_desc=" + summary_desc + ", detail_desc=" + detail_desc + ", display=" + display
				+ ", material=" + material + ", provider=" + provider + ", manufacturer=" + manufacturer
				+ ", manufacturing_date=" + manufacturing_date + ", origin=" + origin + ", main_no=" + main_no
				+ ", sub_no=" + sub_no + ", category_name=" + category_name + "]";
	}

	

	

	
	
	
	
}
