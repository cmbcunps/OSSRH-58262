package com.github.cmbcunps.sdk.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cmbcunps.sdk.cert.CertLoader;
import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;

public class ConfigLoader {
	private static Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

	public static boolean loadConfigAndCert(String merchantNum, String configPath) throws PlatformException {
		if (loadConfig(configPath)) {
			return CertLoader.init(merchantNum);
		} else {
			return false;
		}
	}

	private static boolean loadConfig(String paymentFile) throws PlatformException {
		Properties props = new Properties();
		InputStream inputStream = null;
		try {
			File file = new File(paymentFile);
			logger.info("paymentPath={}", file.getAbsolutePath());
			inputStream = new FileInputStream(file);
			props.load(inputStream);
			for (Object key : props.keySet()) {
				Object value = props.get(key);
				if (key != null && value != null) {
					Configuration.setConfig((String) key, (String) value);
					logger.info(key + "=" + value);
				}
			}
			logger.info("PaymentConfig={}", Configuration.getPaymentConfig());
			return true;
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_001, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
