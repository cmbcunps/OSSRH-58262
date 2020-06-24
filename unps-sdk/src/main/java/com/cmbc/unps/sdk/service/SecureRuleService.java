package com.cmbc.unps.sdk.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cmbc.unps.sdk.exception.ErrorCodeEnum;
import com.cmbc.unps.sdk.exception.PlatformException;
import com.cmbc.unps.sdk.security.Ciphertext;
import com.cmbc.unps.sdk.security.ISecuRule;
import com.cmbc.unps.sdk.security.SecuRuleSM203;
import com.cmbc.unps.sdk.util.Constants;

public class SecureRuleService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ISecuRule secuRuleSM203 = this.getSecureRule("SM203");

	public ISecuRule getSecureRule(String securityType) {
		if (Constants.SECU_RULE_SM203.equals(securityType)) {
			return new SecuRuleSM203();
		} else {
			logger.error("找不到商户安全集成方案-" + securityType);
			return null;
		}
	}

	/**
	 * 签名
	 * 
	 * @param merchantNum
	 * @param securityType
	 * @param busiMap
	 * @return
	 * @throws PlatformException
	 */
	public Ciphertext signRawForInateMerch(String merchantNum, String securityType, Map<String, String> busiMap) throws PlatformException {
		Ciphertext ciphertext = new Ciphertext();
		ciphertext.setBody(JSON.toJSONString(busiMap));
		ciphertext.setInateMerchSign(secuRuleSM203.signRaw(merchantNum, ciphertext.getBody()));
		return ciphertext;
	}

	public Ciphertext signRaw(String merchantNum, String securityType, Map<String, String> busiMap) throws PlatformException {
		Ciphertext ciphertext = new Ciphertext();
		ciphertext.setBody(JSON.toJSONString(busiMap));
		ciphertext.setSign(secuRuleSM203.signRaw(merchantNum, ciphertext.getBody()));
		return ciphertext;
	}

	public Ciphertext signRaw(String merchantNum, String securityType, Ciphertext ciphertext) throws PlatformException {
		ciphertext.setSign(secuRuleSM203.signRaw(merchantNum, ciphertext.getBody()));
		return ciphertext;
	}

	/**
	 * 加密
	 * 
	 * @param merchantNum
	 * @param securityType
	 * @param body
	 * @param sign
	 * @param inateMerchSign
	 * @return
	 * @throws PlatformException
	 */
	public String encodeMsgToString(String merchantNum, String securityType, String body, String sign, String inateMerchSign) throws PlatformException {
		Ciphertext ciphertext = new Ciphertext();
		ciphertext.setBody(body);
		ciphertext.setSign(sign);
		ciphertext.setInateMerchSign(inateMerchSign);
		return secuRuleSM203.encodeMsgToString(merchantNum, ciphertext);
	}

	public String encodeMsgToString(String merchantNum, String securityType, Ciphertext ciphertext) throws PlatformException {
		return secuRuleSM203.encodeMsgToString(merchantNum, ciphertext);
	}

	/**
	 * 解密
	 * 
	 * @param merchantNum
	 * @param securityType
	 * @param businessContext
	 * @return
	 * @throws PlatformException
	 */
	public Ciphertext decodeMsg(String merchantNum, String securityType, String businessContext) throws PlatformException {
		return secuRuleSM203.decodeMsg(merchantNum, businessContext);
	}

	public boolean verifyRaw(String merchantNum, String securityType, String body, String sign) throws PlatformException {
		boolean flag = secuRuleSM203.verifyRaw(merchantNum, body, sign);
		if (!flag) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_007);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> decodeAndVerify(String merchantNum, String securityType, String businessContext) throws PlatformException {
		Map<String, Object> busiMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(businessContext)) {
			return busiMap;
		}
		Ciphertext ciphertext = secuRuleSM203.decodeMsg(merchantNum, businessContext);
		this.verifyRaw(merchantNum, securityType, ciphertext.getBody(), ciphertext.getSign());
		busiMap = JSON.parseObject(ciphertext.getBody(), busiMap.getClass());
		return busiMap;
	}
}
