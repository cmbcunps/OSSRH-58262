package com.cmbc.unps.sdk.agent;

import java.io.File;
import java.util.Map;

import com.cmbc.unps.sdk.cert.CertConfig;
import com.cmbc.unps.sdk.cert.UnpsCert;
import com.cmbc.unps.sdk.config.ConfigConstants;
import com.cmbc.unps.sdk.config.ConfigLoader;
import com.cmbc.unps.sdk.config.Configuration;
import com.cmbc.unps.sdk.exception.PlatformException;
import com.cmbc.unps.sdk.security.Ciphertext;
import com.cmbc.unps.sdk.service.AlipayService;
import com.cmbc.unps.sdk.service.CmbcFileService;
import com.cmbc.unps.sdk.service.CmbcPostService;
import com.cmbc.unps.sdk.service.SecureRuleService;
import com.cmbc.unps.sdk.service.WxpayService;
import com.cmbc.unps.sdk.util.Constants;

public class CMBCUnpsAgent {

	private SecureRuleService secureRuleService = new SecureRuleService();

	private CmbcPostService cmbcPostService = new CmbcPostService(secureRuleService);

	private CmbcFileService postFileService = new CmbcFileService(cmbcPostService);

	private AlipayService alipayService = new AlipayService(postFileService);

	private WxpayService wxpayService = new WxpayService(postFileService);

	/**
	 * 加载参数
	 */
	// public boolean loadConfigAndCert() throws PlatformException {
	// return ConfigLoader.loadConfigAndCert(Configuration.paymentPath);
	// }

	/**
	 * 加载参数
	 * 
	 * @param configPath绝对路径
	 * @return
	 * @throws PlatformException
	 */
	public boolean loadConfigAndCert(String merchantNum, String configPath) throws PlatformException {
		return ConfigLoader.loadConfigAndCert(merchantNum, configPath);
	}

	/**
	 * 添加商户证书
	 * 
	 * @param merchantNum
	 * @param cert
	 * @return
	 * @throws PlatformException
	 */
	public boolean addMerchantCert(String merchantNum, UnpsCert cert) throws PlatformException {
		return CertConfig.addMerchantCert(merchantNum, cert);
	}

	public Ciphertext signRawForInateMerch(String merchantNum, Map<String, String> busiMap) throws PlatformException {
		return secureRuleService.signRawForInateMerch(merchantNum, Constants.SECU_RULE_SM203, busiMap);
	}

	public Ciphertext signRaw(String merchantNum, Ciphertext ciphertext) throws PlatformException {
		return secureRuleService.signRaw(merchantNum, Constants.SECU_RULE_SM203, ciphertext);
	}

	/**
	 * 加密
	 */
	public String encodeMsgToString(String merchantNum, Ciphertext ciphertext) throws PlatformException {
		return secureRuleService.encodeMsgToString(merchantNum, Constants.SECU_RULE_SM203, ciphertext);
	}

	/**
	 * 解密
	 */
	public Map<String, Object> decodeAndVerify(String merchantNum, String businessContext) throws PlatformException {
		return secureRuleService.decodeAndVerify(merchantNum, Constants.SECU_RULE_SM203, businessContext);
	}

	public Map<String, String> postCmbc(Map<String, String> requestMap, String requestUrl) throws PlatformException {
		return cmbcPostService.postCMBC(requestMap, requestUrl);
	}

	/**
	 * 同步交易
	 */
	public Map<String, Object> postTransAll(Map<String, String> requestMap) throws PlatformException {
		return cmbcPostService.postCMBCAll(requestMap, Configuration.getConfig(ConfigConstants.unps_transurl));
	}

	/**
	 * 查询
	 */
	public Map<String, Object> postQueryAll(Map<String, String> requestMap) throws PlatformException {
		return cmbcPostService.postCMBCAll(requestMap, Configuration.getConfig(ConfigConstants.unps_queryurl));
	}

	/**
	 * 下载文件
	 * 
	 * @param requestMap
	 * @param filePath
	 * @return
	 */
	public Map<String, Object> postFileFromCMBC(Map<String, String> requestMap, String filePath) throws PlatformException {
		return postFileService.postFileFromCMBC(requestMap, Configuration.getConfig(ConfigConstants.unps_fileurl), filePath);
	}

	/**
	 * 上传文件
	 */
	public Map<String, Object> postFileToCMBC(Map<String, String> requestMap, File localFile) throws PlatformException {
		return postFileService.postFileToCMBC(requestMap, Configuration.getConfig(ConfigConstants.unps_fileurl), localFile);
	}

	/**
	 * 下载并上传alipay对账文件
	 * 
	 * @param requestMap
	 * @param billDate
	 * @return
	 */
	public Map<String, Object> postAlipayBillToCMBC(Map<String, String> requestMap, String billDate) throws PlatformException {
		return alipayService.postAlipayBillToCMBC(requestMap, Configuration.getConfig(ConfigConstants.unps_fileurl), billDate);
	}

	/**
	 * 下载并上传wxpay对账文件
	 * 
	 * @param requestMap
	 * @param billDate
	 * @return
	 */
	public Map<String, Object> postWxpayBillToCMBC(Map<String, String> requestMap, String billDate) throws PlatformException {
		return wxpayService.postWxpayBillToCMBC(requestMap, Configuration.getConfig(ConfigConstants.unps_fileurl), billDate);
	}

	/**
	 * 异步交易-见证支付/网关支付
	 */
	// public String genSubmitForm(Map<String, String> requestMap) throws PlatformException {
	// return redirectFormService.genSubmitForm(requestMap);
	// }
}