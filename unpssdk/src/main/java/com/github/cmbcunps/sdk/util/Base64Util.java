package com.github.cmbcunps.sdk.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cmbc.cfca.util.Base64;

public class Base64Util {

	public static byte[] base64ToStringBytes(String base64) throws UnsupportedEncodingException {
		if (base64 == null || base64.length() == 0)
			return null;
		return Base64.decode(base64.getBytes("UTF-8"));
	}

	public static String base64ToString(String base64) throws UnsupportedEncodingException {
		return new String(base64ToStringBytes(base64), "UTF-8");
	}

	public static byte[] stringToBase64Bytes(String string) throws UnsupportedEncodingException {
		if (string == null || string.length() == 0)
			return null;
		return Base64.encode(string.getBytes("UTF-8"));
	}

	public static String stringToBase64(String string) throws UnsupportedEncodingException {
		return new String(stringToBase64Bytes(string), "UTF-8");
	}

	public static byte[] getMergeBytes(byte[] base, byte[] merge) {
		merge = Base64.decode(merge);
		if (base == null) {
			return merge;
		}
		int baseLengh = base.length;
		int mergeLengh = merge.length;
		byte[] outByte = new byte[baseLengh + mergeLengh];
		for (int i = 0; i < baseLengh; i++) {
			outByte[i] = base[i];
		}
		for (int i = 0; i < mergeLengh; i++) {
			outByte[i + baseLengh] = merge[i];
		}
		return outByte;
	}

	public static String fileToBase64(String fileName) throws IOException {
		byte[] arr = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			File file = new File(fileName);
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			bos = new ByteArrayOutputStream((int) file.length());
			//
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = bis.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			arr = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new String(Base64.encode(arr), "UTF-8");
	}

	public static String base64ToFile(String base64Code, String fileName) throws IOException {
		FileOutputStream fos = null;
		try {
			File file = new File(fileName);
			File path = file.getParentFile();
			if (path != null && !path.exists()) {
				path.mkdirs();
			}
			fos = new FileOutputStream(file);
			fos.write(Base64.decode(base64Code));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
		return fileName;
	}
}
