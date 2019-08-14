package com.cafe24.shoppingmall.dto;

public class JSONResult {
	private String result;
	private String message;
	private Object data;

	public JSONResult(String result, String message, Object data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static JSONResult success(Object data) {
		return new JSONResult("success", null, data);
	}

	public static JSONResult fail(String message) {
		return new JSONResult("fail", message, false);
	}
	
	

	
	
	
	
}
