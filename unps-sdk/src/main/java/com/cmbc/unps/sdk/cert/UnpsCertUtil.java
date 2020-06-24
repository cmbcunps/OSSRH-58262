package com.cmbc.unps.sdk.cert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmbc.unps.sdk.exception.ErrorCodeEnum;
import com.cmbc.unps.sdk.exception.PlatformException;

import cmbc.cfca.sm2.signature.SM2PrivateKey;
import cmbc.cfca.sm2rsa.common.PKIException;
import cmbc.cfca.util.CertUtil;
import cmbc.cfca.util.KeyUtil;
import cmbc.cfca.x509.certificate.X509Cert;

public class UnpsCertUtil {
	private static Logger logger = LoggerFactory.getLogger(UnpsCertUtil.class);

	public static X509Cert loadCert(String cerPath) throws PlatformException {
		X509Cert cert = null;
		try {
			cert = new X509Cert(cerPath);
			logger.info("加载cer证书成功-" + cerPath);
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_002, e);
		}
		return cert;
	}

	public static X509Cert loadSm2(String sm2Path) throws PlatformException {
		X509Cert cert = null;
		try {
			cert = CertUtil.getCertFromSM2(sm2Path);
			logger.info("加载sm2证书成功-" + sm2Path);
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_002, e);
		}
		return cert;
	}

	public static SM2PrivateKey loadSm2PrivateKey(String sm2Path, String sm2Password) throws PlatformException {
		SM2PrivateKey key = null;
		try {
			key = KeyUtil.getPrivateKeyFromSM2(sm2Path, sm2Password);
			logger.info("加载sm2证书PrivateKey成功-" + sm2Path);
		} catch (PKIException e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_002, e);
		}
		return key;
	}
}
