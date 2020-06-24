package com.cmbc.unps.sdk.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TxtFileUtil {

	public static boolean reCreatFile(String filePath, String fileName) throws Exception {
		try {
			if (!filePath.endsWith("/")) {
				filePath = filePath + "/";
			}
			//
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			//
			File file = new File(filePath + fileName);
			if (file.exists()) {
				file.delete();
			}
			return file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 写文件
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public static boolean appendToTxt(String str, String filePath, String fileName) throws Exception {
		FileOutputStream foStream = null;
		OutputStreamWriter osWriter = null;
		BufferedWriter bWriter = null;
		try {
			if (!filePath.endsWith("/")) {
				filePath = filePath + "/";
			}
			//
			foStream = new FileOutputStream(filePath + fileName, true);
			osWriter = new OutputStreamWriter(foStream, "UTF-8");
			bWriter = new BufferedWriter(osWriter);
			bWriter.newLine();
			bWriter.append(str);
			bWriter.flush();
			osWriter.flush();
			foStream.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (bWriter != null) {
					bWriter.close();
				}
				if (osWriter != null) {
					osWriter.close();
				}
				if (foStream != null) {
					foStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读文件
	 * 
	 * @throws IOException
	 */
	public static List<String> readTxt(String filePath, String fileName) throws Exception {
		List<String> list = new ArrayList<String>();
		//
		FileInputStream fiStream = null;
		InputStreamReader isReader = null;
		BufferedReader bReader = null;
		try {
			if (!filePath.endsWith("/")) {
				filePath = filePath + "/";
			}
			fiStream = new FileInputStream(filePath + fileName);
			isReader = new InputStreamReader(fiStream, "UTF-8");
			bReader = new BufferedReader(isReader);
			//
			String str = null;
			while ((str = bReader.readLine()) != null) {
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (bReader != null) {
					bReader.close();
				}
				if (isReader != null) {
					isReader.close();
				}
				if (fiStream != null) {
					fiStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static boolean writeToTxt(List<String> strList, String filePath, String fileName) throws Exception {
		FileOutputStream foStream = null;
		OutputStreamWriter osWriter = null;
		BufferedWriter bWriter = null;
		try {
			if (!filePath.endsWith("/")) {
				filePath = filePath + "/";
			}
			//
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			//
			foStream = new FileOutputStream(filePath + fileName);
			osWriter = new OutputStreamWriter(foStream, "utf-8");
			bWriter = new BufferedWriter(osWriter);
			for (String str : strList) {
				bWriter.newLine();
				bWriter.append(str);
			}
			bWriter.newLine();
			bWriter.flush();
			osWriter.flush();
			foStream.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (bWriter != null) {
					bWriter.close();
				}
				if (osWriter != null) {
					osWriter.close();
				}
				if (foStream != null) {
					foStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean writeToTxt(byte[] bytes, String filePath, String fileName) throws Exception {
		FileOutputStream fos = null;
		try {
			if (!filePath.endsWith("/")) {
				filePath = filePath + "/";
			}
			//
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			//
			fos = new FileOutputStream(filePath + fileName);
			fos.write(bytes);
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
