package com.github.cmbcunps.sdk.cert;

import java.security.PrivateKey;
import java.security.PublicKey;

import cmbc.cfca.x509.certificate.X509Cert;

public class UnpsCert {
	/** 民生公钥cer */
	public X509Cert cmbcCer = null; // 公钥证书,用于加密报文
	public PublicKey cmbcPublicKey = null; // 公钥,用于验签
	/** 民生私钥sm2 */
	public X509Cert cmbcSm2 = null;
	public PrivateKey cmbcPrivateKey = null;
	public String cmbcSm2Password = null;

	/** 商户公钥cer */
	public X509Cert merCer = null; // 公钥
	public PublicKey merPublicKey = null; // 公钥,用于验签
	/** 商户私钥sm2 */
	public X509Cert merSm2 = null;
	public PrivateKey merPrivateKey = null;
	public String merSm2Password = null;

	/** 商户号 */
	public String merchantNum = null;
}
