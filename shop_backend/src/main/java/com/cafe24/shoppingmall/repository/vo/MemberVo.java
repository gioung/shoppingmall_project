package com.cafe24.shoppingmall.repository.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;

import com.cafe24.shoppingmall.enums.Gender;

public class MemberVo {
	@NotNull @Email
	private String email;
	@NotNull
	private String name;
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
			message="비밀번호는 8자 이상 16자 이하의 알파벳, 숫자, 특수문자를 조합하여 작성해야 합니다.")
	private String password;
	@NotNull
	private String birth;
	@NotNull
	private Gender gender;
	
	private String tel_num;
	@NotNull
	@Pattern (regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message="핸드폰 번호 형식이 아닙니다.")
	private String phone_num;
	
	private String address;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "MemberVo [email=" + email + ", name=" + name + ", password=" + password
				+ ", birth=" + birth + ", gender=" + gender + ", tel_num=" + tel_num + ", phone_num=" + phone_num
				+ "," + "]";
	}

}
