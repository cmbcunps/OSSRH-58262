package com.cmbc.unps.sdk.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cmbc.unps.sdk.config.ConfigConstants;
import com.cmbc.unps.sdk.config.Configuration;

import cmbc.cfca.util.Base64;

public class FileSegmentUtil {
	public static Logger logger = LoggerFactory.getLogger(FileSegmentUtil.class);
	private static final int FILE_SEGMENT_SIZE = 1024 * 1024;// 1M

	public static int getSegmentSize() {
		String segmentSizeStr = Configuration.getConfig(ConfigConstants.unps_segmentSize);
		if (segmentSizeStr == null || segmentSizeStr.length() == 0) {
			return FILE_SEGMENT_SIZE;
		} else {
			try {
				return Integer.parseInt(segmentSizeStr);
			} catch (Exception e) {
				return FILE_SEGMENT_SIZE;
			}
		}
	}

	public static ArrayList<FileSegment> getSegmentInfo(File inputFile) {
		ArrayList<FileSegment> tempList = new ArrayList<FileSegment>();
		long fileSize = inputFile.length();
		long segmentCount = 0;
		if (fileSize % getSegmentSize() == 0) {
			segmentCount = fileSize / getSegmentSize();
		} else {
			segmentCount = fileSize / getSegmentSize() + 1;
		}
		for (int tempIndex = 0; tempIndex < segmentCount; tempIndex++) {
			tempList.add(new FileSegment(fileSize, segmentCount, tempIndex));
		}
		logger.info("{}", JSON.toJSONString(tempList));
		return tempList;
	}

	public static String getBase64String(RandomAccessFile inputRandomFile, final long segmentIndex, final int segmentLength) throws IOException {
		String returnResult = null;
		// seek到指定位置
		inputRandomFile.seek(segmentIndex * getSegmentSize());
		byte[] tempBytes = new byte[segmentLength];
		int realReadLengthInByte = inputRandomFile.read(tempBytes);
		logger.info("read file {}", realReadLengthInByte);
		returnResult = new String(Base64.encode(tempBytes), "utf-8");
		return returnResult;
	}

}
