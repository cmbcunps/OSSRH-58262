package com.cmbc.unps.sdk.security.base;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmbc.unps.sdk.cert.CertConfig;
import com.cmbc.unps.sdk.cert.UnpsCert;

import cmbc.cfca.sm2rsa.common.Mechanism;
import cmbc.cfca.sm2rsa.common.PKIException;
import cmbc.cfca.util.EnvelopeUtil;
import cmbc.cfca.util.SignatureUtil2;
import cmbc.cfca.util.cipher.lib.JCrypto;
import cmbc.cfca.util.cipher.lib.Session;
import cmbc.cfca.x509.certificate.X509Cert;

public class SM2Utils {
	private static Logger logger = LoggerFactory.getLogger(SM2Utils.class);

	private static Session session = null;

	static {
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
		} catch (PKIException e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 加签
	 * 
	 * @param merchantNum
	 * @param sourceData
	 * @return
	 * @throws PKIException
	 * @throws UnsupportedEncodingException
	 */
	public static String signRaw(String merchantNum, String sourceData) throws UnsupportedEncodingException, PKIException {
		UnpsCert merchantCert = CertConfig.getMerchantCert(merchantNum);
		return new String(new SignatureUtil2().p1SignMessage(Mechanism.SM3_SM2, sourceData.getBytes("UTF8"), merchantCert.merPrivateKey, session), "UTF-8");
	}

	/**
	 * 验签
	 * 
	 * @param sourceData
	 * @param signature
	 * @return
	 * @throws PKIException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean verifyRaw(String merchantNum, String sourceData, String signature) throws UnsupportedEncodingException, PKIException {
		UnpsCert cmbcCert = CertConfig.getMerchantCert(merchantNum);
		return new SignatureUtil2().p1VerifyMessage(Mechanism.SM3_SM2, sourceData.getBytes("UTF8"), signature.getBytes(), cmbcCert.cmbcPublicKey, session);
	}

	/**
	 * 加密
	 * 
	 * @param sourceData
	 * @return
	 * @throws PKIException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("deprecation")
	public static String encrypt(String merchantNum, String sourceData) throws UnsupportedEncodingException, PKIException {
		UnpsCert cmbcCert = CertConfig.getMerchantCert(merchantNum);
		X509Cert[] certs = { cmbcCert.cmbcCer };
		byte[] encryptedData = EnvelopeUtil.envelopeMessage(sourceData.getBytes("UTF8"), Mechanism.SM4_CBC, certs);
		return new String(encryptedData, "UTF8");
	}

	/**
	 * 解密
	 * 
	 * @param merchantNum
	 * @param encryptedData
	 * @return
	 * @throws PKIException
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String merchantNum, String encryptedData) throws UnsupportedEncodingException, PKIException {
		UnpsCert merchantCert = CertConfig.getMerchantCert(merchantNum);
		byte[] sourceData = EnvelopeUtil.openEvelopedMessage(encryptedData.getBytes("UTF8"), merchantCert.merPrivateKey, merchantCert.merSm2, session);
		return new String(sourceData, "UTF8");
	}

}
