package com.cmbc.unps.sdk.security;

import com.cmbc.unps.sdk.exception.PlatformException;

public interface ISecuRule {
	/**
	 * 加密
	 * 
	 * @param merchantNum商户号
	 * @param ciphertext
	 * @return
	 * @throws PlatformException
	 */
	String encodeMsgToString(String merchantNum, Ciphertext ciphertext) throws PlatformException;

	Ciphertext decodeMsg(String merchantNum, String businessContext) throws PlatformException;

	String signRaw(String merchantNum, String body) throws PlatformException;

	boolean verifyRaw(String merchantNum, String body, String sign) throws PlatformException;

	/**
	 * 输入： 密文, 输出：业务明文;如果验签或者解密报错，则抛出异常
	 * 
	 * @param cipherContext
	 * @return
	 * @throws PlatformException
	 */

	// String decodeMsgToString(String merchantNum, String businessContext) throws PlatformException;

	/**
	 * 输入： 业务明文, 输出：密文;如果加签或者加密报错，则抛出异常
	 * 
	 * @param cipherContext
	 * @return
	 * @throws PlatformException
	 */
	// String encodeMsgToString(String merchantNum, Map<String, String> busiMap) throws PlatformException;

}
