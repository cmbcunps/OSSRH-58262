package com.github.cmbcunps.sdk.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;
import com.github.cmbcunps.sdk.security.base.SM2Utils;
import com.github.cmbcunps.sdk.util.JsonHelper;
import com.github.cmbcunps.sdk.util.UnpsUtil;

import cmbc.cfca.util.Base64;

/**
 * SM2证书，加密加签方式
 */
public class SecuRuleSM203 implements ISecuRule {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 解密
	 */
	public Ciphertext decodeMsg(String merchantNum, String businessContext) throws PlatformException {
		String plaintext = null;
		Ciphertext ciphertext = null;
		try {
			if (UnpsUtil.isBlank(businessContext)) {
				return null;
			}
			// base64转密文
			businessContext = businessContext.replace("\\n", "");
			String ciphertextStr = new String(Base64.decode(businessContext.getBytes("UTF8")), "UTF-8");
			// 解密
			plaintext = SM2Utils.decrypt(merchantNum, ciphertextStr);
			//
			ciphertext = JSON.parseObject(plaintext, Ciphertext.class);
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_006, e);
		} finally {
			logger.info("解密merchantNum={},businessContext={}", merchantNum, businessContext);
			logger.info("解密merchantNum={},plaintext={}", merchantNum, plaintext);
		}
		return ciphertext;
	}

	/**
	 * 验签
	 */
	public boolean verifyRaw(String merchantNum, String body, String sign) throws PlatformException {
		boolean signOk = false;
		try {
			signOk = SM2Utils.verifyRaw(merchantNum, body, sign);
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_007, e);
		} finally {
			logger.info("验签merchantNum={},body={}", merchantNum, body);
			logger.info("验签merchantNum={},sign={}", merchantNum, sign);
			logger.info("验签merchantNum={},signOk={}", merchantNum, signOk);
		}
		return signOk;
	}

	/**
	 * 加密
	 */
	public String encodeMsgToString(String merchantNum, Ciphertext ciphertext) throws PlatformException {
		String plaintext = JsonHelper.objectToJson(ciphertext);// 待加密字符串
		String ciphertextStr = null;
		String businessContext = "";
		try {
			ciphertextStr = SM2Utils.encrypt(merchantNum, plaintext);
			businessContext = new String(Base64.encode(ciphertextStr.getBytes("UTF8")), "utf-8");
			businessContext = businessContext.replaceAll("\\n", "");
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_005, e);
		} finally {
			logger.info("加密merchantNum={},plaintext={}", merchantNum, plaintext);
			logger.info("加密merchantNum={},businessContext={}", merchantNum, businessContext);
		}
		return businessContext;
	}

	/**
	 * 签名
	 */
	public String signRaw(String merchantNum, String body) throws PlatformException {
		String sign = null;
		try {
			sign = SM2Utils.signRaw(merchantNum, body);
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_004, e);
		} finally {
			logger.info("签名merchantNum={},body={}", merchantNum, body);
			logger.info("签名merchantNum={},sign={}", merchantNum, sign);
		}
		return sign;
	}

}
