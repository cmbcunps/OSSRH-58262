package com.github.cmbcunps.sdk.config;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

	public static final String paymentPath = "config/payment.ini";

	private static Map<String, String> paymentConfig = new HashMap<String, String>();

	/* ******************************************************************* */
	public static String getConfig(String key) {
		return paymentConfig.get(key);
	}

	public static void setConfig(String key, String value) {
		paymentConfig.put(key, value);
	}

	public static Map<String, String> getPaymentConfig() {
		return paymentConfig;
	}
}
