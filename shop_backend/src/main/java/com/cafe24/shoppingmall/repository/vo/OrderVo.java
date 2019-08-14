package com.cafe24.shoppingmall.repository.vo;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class OrderVo {
	private long order_no;
	private Long[] seq_no;
	private String recv_name;
	private String recv_tel;
	@NotNull
	private String destination;
	@NotNull
	private String ord_name;
	@NotNull
	@Pattern (regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message="핸드폰 번호 형식이 아닙니다.")
	private String ord_tel;
	@NotNull
	private boolean is_same;
	private String order_date;
	@NotNull
	private long pay;
	@NotNull
	private boolean ismember = false;
	@NotNull
	private boolean iscart;
	private String id;
	private List<OrderedProductVo> orderList;
	
	public OrderVo() {
		
	}
	
	//장바구니에서 상품 구입
	public OrderVo(long order_no, String recv_name, String recv_tel, String destination, String ord_name,
			String ord_tel, boolean is_same, String id) {
		this.order_no = order_no;
		this.recv_name = recv_name;
		this.recv_tel = recv_tel;
		this.destination = destination;
		this.ord_name = ord_name;
		this.ord_tel = ord_tel;
		this.is_same = is_same;
		this.id = id;
	}
	
	//상품페이지에서 상품 구입
	public OrderVo(long order_no, String recv_name, String recv_tel, String destination, String ord_name,
			String ord_tel, boolean is_same,boolean iscart, String id) {
		super();
		this.order_no = order_no;
		this.recv_name = recv_name;
		this.recv_tel = recv_tel;
		this.destination = destination;
		this.ord_name = ord_name;
		this.ord_tel = ord_tel;
		this.is_same = is_same;
		this.iscart = iscart;
		this.id = id;
	}
	public long getOrder_no() {
		return order_no;
	}
	public void setOrder_no(long order_no) {
		this.order_no = order_no;
	}
	public String getRecv_name() {
		return recv_name;
	}
	public void setRecv_name(String recv_name) {
		this.recv_name = recv_name;
	}
	public String getRecv_tel() {
		return recv_tel;
	}
	public void setRecv_tel(String recv_tel) {
		this.recv_tel = recv_tel;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOrd_name() {
		return ord_name;
	}
	public void setOrd_name(String ord_name) {
		this.ord_name = ord_name;
	}
	public String getOrd_tel() {
		return ord_tel;
	}
	public void setOrd_tel(String ord_tel) {
		this.ord_tel = ord_tel;
	}
	public boolean isIs_same() {
		return is_same;
	}
	public void setIs_same(boolean is_same) {
		this.is_same = is_same;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public long getPay() {
		return pay;
	}
	public void setPay(long pay) {
		this.pay = pay;
	}
	public boolean isIsmember() {
		return ismember;
	}
	public void setIsmember(boolean ismember) {
		this.ismember = ismember;
	}
	
	public boolean isIscart() {
		return iscart;
	}
	public void setIscart(boolean iscart) {
		this.iscart = iscart;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<OrderedProductVo> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderedProductVo> orderList) {
		this.orderList = orderList;
	}
	
	public Long[] getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(Long[] seq_no) {
		this.seq_no = seq_no;
	}

	@Override
	public String toString() {
		return "OrderVo [order_no=" + order_no + ", seq_no=" + Arrays.toString(seq_no) + ", recv_name=" + recv_name
				+ ", recv_tel=" + recv_tel + ", destination=" + destination + ", ord_name=" + ord_name + ", ord_tel="
				+ ord_tel + ", is_same=" + is_same + ", order_date=" + order_date + ", pay=" + pay + ", ismember="
				+ ismember + ", iscart=" + iscart + ", id=" + id + ", orderList=" + orderList + "]";
	}
	
	
	
	
	
	
}
