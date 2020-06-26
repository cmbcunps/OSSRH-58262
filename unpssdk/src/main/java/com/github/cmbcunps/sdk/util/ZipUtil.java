package com.github.cmbcunps.sdk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;

public class ZipUtil {

	private static final int BUFFER_SIZE = 1024;

	public static boolean zip(File tarFile, File reconFile, String entryName) throws PlatformException {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			if (!reconFile.exists()) {
				reconFile.createNewFile();
			}
			ZipEntry zipEntry = new ZipEntry(entryName);
			fos = new FileOutputStream(reconFile);
			zos = new ZipOutputStream(fos, Charset.forName("utf-8"));
			zos.putNextEntry(zipEntry);
			//
			fis = new FileInputStream(tarFile);
			bis = new BufferedInputStream(fis);
			int readLength = 0;// 每次读出来的长度
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((readLength = bis.read(buffer, 0, 1024)) != -1) {
				zos.write(buffer, 0, readLength);
			}
			zos.flush();
			return true;
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_017, e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (zos != null) {
					zos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean unzip(File srcFile, File tarFile) throws PlatformException {
		File tarFilePath = new File(tarFile.getParent());
		if (!tarFilePath.exists()) {
			tarFilePath.mkdirs();
		}
		//
		ZipFile zipFile = null;
		InputStream ins = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			zipFile = new ZipFile(srcFile, Charset.forName("gbk"));
			// zipFile = new ZipFile(srcFile);
			Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = enumeration.nextElement();
				if (!zipEntry.getName().contains("汇总")) {
					break;
				}
			}
			ins = zipFile.getInputStream(zipEntry);
			fos = new FileOutputStream(tarFile);
			bos = new BufferedOutputStream(fos);
			//
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytes = -1;
			while ((bytes = ins.read(buffer)) != -1) {
				bos.write(buffer, 0, bytes);
				bos.flush();
			}
			bos.flush();
			return true;
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_016, e);
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
