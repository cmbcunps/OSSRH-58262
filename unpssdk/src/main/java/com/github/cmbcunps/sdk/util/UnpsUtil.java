package com.github.cmbcunps.sdk.util;

import java.util.Date;
import java.util.Map;

import com.github.cmbcunps.sdk.config.ConfigConstants;
import com.github.cmbcunps.sdk.config.Configuration;

/**
 * 
 * @author Administrator
 * 
 */
public class UnpsUtil {
	/**
	 * 版本号 version 发起来源 source 商户编号 merchantNum 商户流水 merchantSeq 交易日期 transDate 交易时间 transTime 交易编码 transCode 安全类型 securityType 通用备用字段 reserveJson 业务报文 businessContext
	 * 
	 * @param strBiz
	 * @return
	 */
	public static Map<String, String> initHeadData(String strBiz) {
		Map<String, String> requestMap = JsonHelper.jsonToMap(strBiz);
		Date date = new Date();
		//
		// requestMap.put(Constants.merchantNum, Configuration.getConfig(ConfigConstants.unps_merchantnum)); // 商户ID
		requestMap.put(Constants.transDate,
				requestMap.get(Constants.transDate) == null || "".equals(requestMap.get(Constants.transDate)) ? DateFormter.formatDate(date) : requestMap.get(Constants.transDate)); // 交易日期 
		requestMap.put(Constants.transTime,
				requestMap.get(Constants.transTime) == null || "".equals(requestMap.get(Constants.transTime)) ? DateFormter.formatTime(date) : requestMap.get(Constants.transTime)); // 交易时间
		requestMap.put(Constants.securityType, Configuration.getConfig(ConfigConstants.unps_securitytype));
		return requestMap;
	}

	public static void splidReqMap(Map<String, String> requestMap, Map<String, String> submitMap, Map<String, String> busiMap) {
		busiMap.putAll(requestMap);
		for (String key : Constants.HAED_PARAMS) {
			String value = requestMap.get(key);
			if (value != null && !"".equals(value)) {
				submitMap.put(key, value);
				busiMap.remove(key);
			}
		}
	}

	// public static Map<String, String> jsonToStrMap(String jsonStr) {
	// Map<String, String> responseMap = new HashMap<String, String>();
	// JSONObject jsonObject = JSON.parseObject(jsonStr);
	// for (Entry<String, Object> entry : jsonObject.entrySet()) {
	// responseMap.put(entry.getKey(), jsonObject.getString(entry.getKey()));
	// }
	// return responseMap;
	// }

	public static String[] getListItems(String str, String split) {
		if (str != null) {
			str = str.trim();
		}
		if (str == null || str.length() == 0) {
			return new String[0];
		}
		String items[] = str.split(split);
		return items;
	}

	public static String[] getItem(String str, String split) {
		if (str != null) {
			str = str.trim();
		}
		if (str == null || str.length() == 0) {
			return new String[0];
		}
		String items[] = str.split(split, -1);// 不要加-1
		return items;
	}

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if ((cs == null) || ((strLen = cs.length()) == 0))
			return true;
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(cs.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	// public static Map<String, String> itemsToMap(String str) {
	// Map<String, String> map = new HashMap<String, String>();
	// if (str == null || str.length() == 0) {
	// return map;
	// } else {
	// String[] listItems = getListItems(str, "[|]");
	// for (String itemStr : listItems) {
	// String[] item = getItem(itemStr, "=");
	// map.put(item[0], item[1]);
	// }
	// return map;
	// }
	// }

	public static String getQrPayURL(String str) {
		String qrPayURL = null;
		if (str == null || str.length() == 0) {
			return qrPayURL;
		} else {
			String[] listItems = getListItems(str, "[|]");
			for (String itemStr : listItems) {
				if (itemStr != null && itemStr.startsWith("picURL=")) {
					qrPayURL = itemStr.replaceFirst("picURL=", "");
					return qrPayURL;
				}
			}
			return null;
		}
	}

}
