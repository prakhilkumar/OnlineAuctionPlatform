package com.project.onlineauction.exceptions;

public class AuctionException extends RuntimeException {
	
	private String code;
	private String msg;
	
	public AuctionException(String msg,String code) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
