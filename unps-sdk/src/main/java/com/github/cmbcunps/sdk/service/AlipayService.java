package com.github.cmbcunps.sdk.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.github.cmbcunps.sdk.config.ConfigConstants;
import com.github.cmbcunps.sdk.config.Configuration;
import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;
import com.github.cmbcunps.sdk.util.Constants;
import com.github.cmbcunps.sdk.util.ZipUtil;

public class AlipayService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private CmbcFileService postFileService = null;

	public AlipayService(CmbcFileService postFileService) {
		this.postFileService = postFileService;
	}

	/**
	 * 
	 * @param requestMap
	 * @param billDate,yyyy-MM-dd
	 * @return
	 * @throws PlatformException
	 */
	public Map<String, Object> postAlipayBillToCMBC(Map<String, String> requestMap, String requestUrl, String billDate) throws PlatformException {
		String publicKey = Configuration.getConfig(ConfigConstants.unps_alipayPublickey);
		String privateKey = Configuration.getConfig(ConfigConstants.unps_alipayPrivatekey);
		String appId = Configuration.getConfig(ConfigConstants.unps_alipayAppid);
		String alipayUrl = Configuration.getConfig(ConfigConstants.unps_alipayUrl);
		String filePath_tmp = Configuration.getConfig(ConfigConstants.filePath_tmp);
		//
		if (null == billDate || billDate.length() != 10) {
			// throw new Exception("账单日期为空或格式不合法，格式为yyyy-MM-dd,您的输入为:" + bill_date);
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_008, "billDate");
		}
		// 1.
		String billDownloadUrl = null;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
			AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{" + "\"bill_type\":\"trade\"," + "\"bill_date\":\"" + billDate + "\"" + "  }");
			AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
			//
			if (response == null) {
				logger.info("alipay response is null");
				throw new PlatformException(ErrorCodeEnum.UNPS_CORE_014, "alipay response is null");
			}
			if (response.isSuccess()) {
				billDownloadUrl = response.getBillDownloadUrl();
			} else {
				logger.info("billDownloadUrl,success={},msg={},submsg={}", response.isSuccess(), response.getMsg(), response.getSubMsg());
				throw new PlatformException(ErrorCodeEnum.UNPS_CORE_014, "billDownload error");
			}
		} catch (Exception e) {
			logger.error("get alipay billDownloadUrl error,{}", e);
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_014, e);
		}
		// 2.下载
		File srcFile = new File(filePath_tmp + "alipay_" + billDate + ".zip");
		InputStream fis = null;
		FileOutputStream fos = null;
		HttpURLConnection httpUrlConnection = null;
		try {
			URL url = new URL(billDownloadUrl);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(10000);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("CHARSET", "UTF-8");
			httpUrlConnection.connect();
			fis = httpUrlConnection.getInputStream();
			//
			if (srcFile.exists()) {
				srcFile.delete();
			}
			//
			byte[] fileData = new byte[1024];
			int code;
			fos = new FileOutputStream(srcFile);
			while ((code = fis.read(fileData)) != -1) {
				fos.write(fileData, 0, code);
				fos.flush();
			}
			fos.flush();
		} catch (Exception e) {
			logger.error("download alipay bill error,{}", e);
			if (srcFile.exists()) {
				srcFile.delete();
			}
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_014, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (httpUrlConnection != null) {
				httpUrlConnection.disconnect();
			}
		}
		//
		String contractId = requestMap.get(Constants.contractId);
		String settTime = requestMap.get(Constants.settTime);
		String entryName = "recon_" + contractId + "_" + settTime + "000_HZ64";
		File tarFile = new File(filePath_tmp + entryName);
		File reconFile = new File(filePath_tmp + entryName + ".zip");
		ZipUtil.unzip(srcFile, tarFile);
		ZipUtil.zip(tarFile, reconFile, entryName);
		//
		// srcFile.delete();
		// tarFile.delete();
		// 3.上传
		// return postFileService.postFileToCMBC(requestMap, reconFile, true);
		return postFileService.postFileToCMBC(requestMap, requestUrl, reconFile);
	}

}