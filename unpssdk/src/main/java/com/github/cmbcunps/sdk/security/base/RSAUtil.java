package com.github.cmbcunps.sdk.security.base;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.github.cmbcunps.sdk.config.ConfigConstants;
import com.github.cmbcunps.sdk.config.Configuration;
import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;
import com.github.cmbcunps.sdk.util.Base64Util;

import cmbc.cfca.org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 
 * RSA 加密解密算法
 * 
 * @author Administrator
 * 
 */
public class RSAUtil {

	private static Cipher cipher_Enc;
	private static Cipher cipher_Dec;

	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
			cipher_Enc = Cipher.getInstance("RSA/ECB/NoPadding", "BC");

			String cmbcPubKey = Configuration.getConfig(ConfigConstants.unps_cmbc_rsapubkey);
			cipher_Enc.init(1, getPublicKey(cmbcPubKey));

			String cmbcRsaKey = Configuration.getConfig(ConfigConstants.unps_cmbc_rsaprikey);
			cipher_Dec.init(2, getPrivateKey(cmbcRsaKey));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PublicKey getPublicKey(String key) throws Exception {
		// byte[] keyBytes = base64Decode(key);
		byte[] keyBytes = Base64Util.base64ToStringBytes(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {
		// byte[] keyBytes = base64Decode(key);
		byte[] keyBytes = Base64Util.base64ToStringBytes(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		// return base64Encode(keyBytes);
		return Base64Util.stringToBase64(new String(keyBytes, "UTF-8"));
	}

	public static String encrypt(String plainText) throws PlatformException {
		String str = null;
		try {
			byte[] enBytes = cipher_Enc.doFinal(plainText.getBytes("UTF-8"));
			// str = base64Encode(enBytes);
			str = Base64Util.stringToBase64(new String(enBytes, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_019);
		}
		return str;
	}

	public static String decrypt(String enStr) throws PlatformException {
		try {
			// byte[] deBytes = cipher_Dec.doFinal(base64Decode(enStr));
			byte[] deBytes = cipher_Dec.doFinal(Base64Util.base64ToStringBytes(enStr));
			return new String(deBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_020);
		}
	}

	/**
	 * @param bytes
	 * @return
	 */
	// public static byte[] base64Decode(String mi) {
	// return Base64.decodeBase64(mi.getBytes());
	// }

	/**
	 * 二进制数据编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	// public static String base64Encode(byte[] ming) {
	// return new String(Base64.encodeBase64(ming));
	// }

}