package com.github.cmbcunps.sdk.security.base;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cmbc.cfca.org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES128
 * 
 */
public class AESUtils {
	public static String encCodeing = "UTF-8";
	public static final String AES_ALGORITHM = "AES";
	public static final String AES_ALGORITHM_ECB = "AES/ECB/PKCS7Padding";// 兼容IOS写法
	public static final String DES_ALGORITHM = "DESede";

	/**
	 * 支持以下任意一种算法
	 * 
	 * <pre>
	 * PBEWithMD5AndDES 
	 * PBEWithMD5AndTripleDES 
	 * PBEWithSHA1AndDESede
	 * PBEWithSHA1AndRC2_40
	 * </pre>
	 */
	public static final String PBE_ALGORITHM = "PBEWITHMD5andDES";
	public static final byte[] DEFAULT_AES_KEY = { 69, -35, -53, -45, 23, 106, 60, 49, -40, 58, 62, -75, 96, -9, 12,
			101 };

	private static byte[] _encrypt(String plaintext, byte[] salt) throws PlatformException {
		try {
			SecretKeySpec key = getAesKey(salt);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM_ECB, new BouncyCastleProvider());
			byte[] byteContent = plaintext.getBytes(encCodeing);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_005);
		}
	}

	/**
	 * 加密
	 * 
	 * @param plaintext
	 * @param salt
	 * @return String
	 * @throws PlatformException
	 */
	public static String encrypt(String plaintext) throws PlatformException {
		return byte2Hex(_encrypt(plaintext, DEFAULT_AES_KEY));
	}

	/**
	 * 解密
	 * 
	 * @param ciphertext
	 * @param salt
	 * @return byte[] @
	 * @throws PlatformException
	 */
	private static byte[] _decrypt(byte[] ciphertext, byte[] salt) throws PlatformException {
		try {
			SecretKeySpec key = getAesKey(salt);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM_ECB, new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(ciphertext);
			return result;
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_006);
		}
	}

	public static String decrypt(String ciphertext) throws PlatformException {
		return new String(_decrypt(hex2Byte(ciphertext), DEFAULT_AES_KEY));
	}

	private static String byte2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] hex2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String encrypt(String plaintext, Object key) throws PlatformException {
		byte[] bts = null;
		if (key != null) {
			bts = (byte[]) key;
		}
		return byte2Hex(_encrypt(plaintext, bts));
	}

	public static String decrypt(String ciphertext, Object key) throws PlatformException {
		byte[] bts = null;
		if (key != null) {
			bts = (byte[]) key;
		}
		try {
			return new String(_decrypt(hex2Byte(ciphertext), bts), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_006);
		}
	}

	public static int getKeyLen() {
		return DEFAULT_AES_KEY.length;
	}

	public static SecretKeySpec getAesKey(byte[] salt) throws PlatformException {
		SecretKeySpec key = null;
		if (salt != null) {
			if (salt.length != DEFAULT_AES_KEY.length) {
				throw new PlatformException(ErrorCodeEnum.UNPS_CORE_018);
			}
			key = new SecretKeySpec(salt, AES_ALGORITHM);
		} else {
			key = new SecretKeySpec(DEFAULT_AES_KEY, AES_ALGORITHM);
		}
		return key;
	}
}
