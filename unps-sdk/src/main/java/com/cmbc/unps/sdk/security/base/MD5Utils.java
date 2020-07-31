package com.cmbc.unps.sdk.security.base;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Added by Mlkui@20140514
public class MD5Utils {
	private static String MD5 = "MD5";

	static MessageDigest getDigest(String arg3) {
		try {
			return MessageDigest.getInstance(arg3);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new RuntimeException(noSuchAlgorithmException.getMessage());
		}
	}

	private static String getHexString(byte[] arg4) {
		return String.format("%032x", new BigInteger(1, arg4));
	}

	public static String md5Digest(String arg2) {
		if (arg2 == null) {
			return null;
		}
		MessageDigest messageDigest = MD5Utils.getDigest(MD5Utils.MD5);
		messageDigest.update(arg2.getBytes());
		return MD5Utils.getHexString(messageDigest.digest());
	}

	public static String md5Digest(InputStream arg5) {
		try {
			MessageDigest messageDigest = MD5Utils.getDigest(MD5Utils.MD5);
			byte[] array_b = new byte[131072];
			int i;
			for (i = arg5.read(array_b, 0, 131072); i > -1; i = arg5.read(array_b, 0, 131072)) {
				messageDigest.update(array_b, 0, i);
			}
			return MD5Utils.getHexString(messageDigest.digest());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String md5Digest(InputStream arg4, byte[] arg5) {
		try {
			MessageDigest messageDigest = MD5Utils.getDigest(MD5Utils.MD5);
			int i;
			for (i = arg4.read(arg5, 0, arg5.length); i > -1; i = arg4.read(arg5, 0, arg5.length)) {
				messageDigest.update(arg5, 0, i);
			}
			return MD5Utils.getHexString(messageDigest.digest());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}