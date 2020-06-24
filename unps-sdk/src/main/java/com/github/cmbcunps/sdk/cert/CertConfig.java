package com.github.cmbcunps.sdk.cert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertConfig {
	private static Logger logger = LoggerFactory.getLogger(CertConfig.class);
	/**
	 * 默认证书
	 */
	private static UnpsCert merchantCert = new UnpsCert();
	/**
	 * 商户证书
	 */
	public static Map<String, UnpsCert> merchantCertMap = new ConcurrentHashMap<String, UnpsCert>();

	public static UnpsCert getMerchantCert(String merchantNum) {
		UnpsCert cert = merchantCertMap.get(merchantNum);
		if (cert == null) {
			cert = merchantCert;
			logger.info("[" + merchantNum + "]使用默认证书");
		} else {
			logger.info("[" + merchantNum + "]使用自定义证书");
		}
		return cert;
	}

	public static boolean addMerchantCert(String merchantNum, UnpsCert cert) {
		if (cert.merCer != null && cert.merPublicKey != null && cert.merSm2 != null && cert.merPrivateKey != null) {
			if (cert.cmbcCer == null) {
				cert.cmbcCer = merchantCert.cmbcCer;// 民生公钥为空，取默认公钥
				cert.cmbcPublicKey = merchantCert.cmbcPublicKey;
			}
			merchantCertMap.put(merchantNum, cert);
			logger.info("[" + merchantNum + "]添加证书成功");
			return true;
		} else {
			return false;
		}
	}

	public static boolean addDefaultMerchantCert(UnpsCert cert) {
		if (cert.merCer != null && cert.merPublicKey != null && cert.merSm2 != null && cert.merPrivateKey != null && cert.cmbcCer != null && cert.cmbcPublicKey != null) {
			merchantCert = cert;
			logger.info("添加默认证书成功");
			return true;
		} else {
			return false;
		}
	}
}
