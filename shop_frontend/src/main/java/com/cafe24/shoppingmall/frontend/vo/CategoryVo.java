package com.cafe24.shoppingmall.frontend.vo;

import javax.validation.constraints.NotNull;

public class CategoryVo {
	@NotNull(message = "메인번호는 항상 존재해야 합니다.")
	private long main_no;
	private long sub_no;
	@NotNull(message = "카테고리 이름은 항상 존재해야 합니다.")
	private String name;
	
	//기본 생성자
	public CategoryVo() {
		
	}
	//메인 카테고리 생성자
	public CategoryVo(@NotNull(message = "메인번호는 항상 존재해야 합니다.") long main_no,
			@NotNull(message = "카테고리 이름은 항상 존재해야 합니다.") String name) {
		this.main_no = main_no;
		this.name = name;
	}
	//서브 카테고리 생성자
	public CategoryVo(@NotNull(message = "메인번호는 항상 존재해야 합니다.") long main_no, long sub_no,
			@NotNull(message = "카테고리 이름은 항상 존재해야 합니다.") String name) {
		this.main_no = main_no;
		this.sub_no = sub_no;
		this.name = name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CategoryVo [main_no=" + main_no + ", sub_no=" + sub_no + ", name=" + name + "]";
	}
	
	
	
	
}
