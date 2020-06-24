package com.cmbc.unps.sdk.service;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.cmbc.unps.sdk.config.ConfigConstants;
import com.cmbc.unps.sdk.config.Configuration;
import com.cmbc.unps.sdk.exception.ErrorCodeEnum;
import com.cmbc.unps.sdk.exception.PlatformException;
import com.cmbc.unps.sdk.file.FileSegment;
import com.cmbc.unps.sdk.file.FileSegmentUtil;
import com.cmbc.unps.sdk.util.Base64Util;
import com.cmbc.unps.sdk.util.Constants;
import com.cmbc.unps.sdk.util.TxtFileUtil;

public class CmbcFileService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private CmbcPostService cmbcPostService = null;

	public CmbcFileService(CmbcPostService cmbcPostService) {
		this.cmbcPostService = cmbcPostService;
	}

	public Map<String, Object> postFileToCMBC(Map<String, String> requestMap, String requestUrl, File localFile) throws PlatformException {
		Map<String, Object> cmbcResponse = null;
		RandomAccessFile inputRandomFile = null;
		try {
			List<FileSegment> list = FileSegmentUtil.getSegmentInfo(localFile);
			inputRandomFile = new RandomAccessFile(localFile, "r");
			for (FileSegment segment : list) {
				requestMap.put(Constants.fileSize, "" + inputRandomFile.length());
				requestMap.put(Constants.endFlag, segment.getEndFlag());
				requestMap.put(Constants.segmentIndex, "" + segment.getSegmentIndex());
				requestMap.put(Constants.segmentLength, "" + segment.getSegmentLength());
				requestMap.put(Constants.segmentBase64Str, FileSegmentUtil.getBase64String(inputRandomFile, segment.getSegmentIndex(), segment.getSegmentLength()));
				cmbcResponse = cmbcPostService.postCMBCAll(requestMap, Configuration.getConfig(ConfigConstants.unps_fileurl));
				//
				if (checkResult(cmbcResponse)) {
					return cmbcResponse;
				}
			}
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_015, e);
		} finally {
			try {
				if (inputRandomFile != null) {
					inputRandomFile.close();
				}
				// if (isDelete && file != null && file.exists()) {
				// logger.error("{}", file.delete());
				// }
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return cmbcResponse;
	}

	public Map<String, Object> postFileFromCMBC(Map<String, String> requestMap, String requestUrl, String filePath) throws PlatformException {
		Map<String, Object> responseMap = cmbcPostService.postCMBCAll(requestMap, requestUrl);
		//
		if (checkResult(responseMap)) {
			return responseMap;
		}
		String fileName = (String) responseMap.get(Constants.fileName);
		String currentSegmentContentBase64String = null;
		byte[] base64Merge = null;
		byte[] fileBytes = null;
		try {
			currentSegmentContentBase64String = (String) responseMap.get(Constants.currentSegmentContentBase64String);
			base64Merge = Base64Util.base64ToStringBytes(currentSegmentContentBase64String);
			fileBytes = Base64Util.getMergeBytes(fileBytes, base64Merge);
			//
			JSONArray fileSegments = (JSONArray) responseMap.get(Constants.fileAllSegments);
			int maxCount = fileSegments.size();
			for (int count = 1; count < maxCount; count++) {
				//
				logger.info("maxCount:" + maxCount + ",count:" + count);
				//
				requestMap.put(Constants.segmentIndex, count + "");// +1
				Map<String, Object> respMapN = cmbcPostService.postCMBCAll(requestMap, requestUrl);
				if (checkResult(responseMap)) {
					return responseMap;
				}
				currentSegmentContentBase64String = (String) respMapN.get(Constants.currentSegmentContentBase64String);
				base64Merge = Base64Util.base64ToStringBytes(currentSegmentContentBase64String);
				fileBytes = Base64Util.getMergeBytes(fileBytes, base64Merge);
			}
			TxtFileUtil.writeToTxt(fileBytes, filePath, fileName);
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_014, e);
		}
		responseMap.put(Constants.localFile, filePath + fileName);
		return responseMap;
	}

	private boolean checkResult(Map<String, Object> responseMap) {
		String gateReturnType = (String) responseMap.get(Constants.gateReturnType);
		String busiType = (String) responseMap.get(Constants.busiType);
		if (!"S".equalsIgnoreCase(gateReturnType) || !"S".equalsIgnoreCase(busiType)) {
			return true;
		}
		return false;
	}

}