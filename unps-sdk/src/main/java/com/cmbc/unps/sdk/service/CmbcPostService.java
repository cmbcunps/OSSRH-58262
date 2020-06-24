package com.cmbc.unps.sdk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmbc.unps.sdk.config.ConfigConstants;
import com.cmbc.unps.sdk.config.Configuration;
import com.cmbc.unps.sdk.exception.ErrorCodeEnum;
import com.cmbc.unps.sdk.exception.PlatformException;
import com.cmbc.unps.sdk.http.CMBCPostClient;
import com.cmbc.unps.sdk.security.Ciphertext;
import com.cmbc.unps.sdk.util.Base64Util;
import com.cmbc.unps.sdk.util.Constants;
import com.cmbc.unps.sdk.util.JsonHelper;
import com.cmbc.unps.sdk.util.TxtFileUtil;
import com.cmbc.unps.sdk.util.UnpsUtil;

public class CmbcPostService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private SecureRuleService secureRuleService = null;

	public CmbcPostService(SecureRuleService secureRuleService) {
		this.secureRuleService = secureRuleService;
	}

	public Map<String, Object> postCMBCAll(Map<String, String> requestMap, String requestUrl) throws PlatformException {
		String merchantNum = (String) requestMap.get(Constants.merchantNum);
		String securityType = (String) requestMap.get(Constants.securityType);
		//
		Map<String, String> submitMap = new HashMap<String, String>();
		Map<String, String> busiMap = new HashMap<String, String>();
		// fileContentStr-fileContent
		initFileContent(requestMap);
		//
		UnpsUtil.splidReqMap(requestMap, submitMap, busiMap);
		// 签名
		Ciphertext ciphertext = secureRuleService.signRaw(merchantNum, securityType, busiMap);
		// 加密
		submitMap.put(Constants.businessContext, secureRuleService.encodeMsgToString(merchantNum, securityType, ciphertext));
		// 请求
		Map<String, String> responseMap = this.postCMBC(submitMap, requestUrl);
		// 解密
		String businessContext = responseMap.get(Constants.businessContext);
		Ciphertext resCiphertext = secureRuleService.decodeMsg(merchantNum, securityType, businessContext);
		//
		secureRuleService.verifyRaw(merchantNum, securityType, resCiphertext.getBody(), resCiphertext.getSign());
		//
		Map<String, Object> responseBusiMap = JsonHelper.jsonToObjMap(resCiphertext.getBody());
		//
		responseBusiMap.putAll(responseMap);
		return responseBusiMap;
	}

	public Map<String, String> postCMBC(Map<String, String> submitMap, String requestUrl) throws PlatformException {
		CMBCPostClient client = new CMBCPostClient();
		return client.postCmbc(requestUrl, submitMap);
	}

	private void initFileContent(Map<String, String> requestMap) throws PlatformException {
		String fileContentStr = (String) requestMap.get("fileContentStr");
		if (fileContentStr != null && fileContentStr.length() > 0) {
			String fileContent = "";
			try {
				String merchantSeq = (String) requestMap.get(Constants.merchantSeq);
				String filePath_req = Configuration.getConfig(ConfigConstants.filePath_req);
				String[] fileContentStrArray = fileContentStr.split("[/^]");
				List<String> strList = new ArrayList<String>();
				for (String str : fileContentStrArray) {
					strList.add(str);
				}
				TxtFileUtil.writeToTxt(strList, filePath_req, merchantSeq + ".txt");
				fileContent = Base64Util.fileToBase64(filePath_req + merchantSeq + ".txt");
			} catch (Exception e) {
				throw new PlatformException(ErrorCodeEnum.UNPS_CORE_013, e);
			}
			logger.info("fileContent:" + fileContent);
			requestMap.put("fileContent", fileContent);
			requestMap.remove("fileContentStr");
		}
	}

}
