package com.github.cmbcunps.sdk.util;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

public class JsonHelper {

	public static String objectToJson(Object inputObject) {
		return JSON.toJSONString(inputObject);
	}

	public static <T> Object jsonToObject(String inputJson, Class<T> targetObject) {
		return JSON.parse(inputJson);
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> jsonToMap(String jsonStr) {
		return JSON.parseObject(jsonStr, new HashMap<String, String>().getClass());
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> jsonToObjMap(String jsonStr) {
		return JSON.parseObject(jsonStr, new HashMap<String, Object>().getClass());
	}
}