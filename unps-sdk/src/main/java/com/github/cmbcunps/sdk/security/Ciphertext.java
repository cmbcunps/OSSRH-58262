package com.github.cmbcunps.sdk.security;

public class Ciphertext {
	/**
	 * 明文
	 */
	private String body;
	/**
	 * 一级商户签名
	 */
	private String sign;
	/**
	 * 二级商户签名
	 */
	private String inateMerchSign;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getInateMerchSign() {
		return inateMerchSign;
	}

	public void setInateMerchSign(String inateMerchSign) {
		this.inateMerchSign = inateMerchSign;
	}

}
