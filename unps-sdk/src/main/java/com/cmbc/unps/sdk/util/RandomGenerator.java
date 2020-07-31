package com.cmbc.unps.sdk.util;

import java.util.Date;

import com.cmbc.unps.sdk.security.base.MD5Utils;

public class RandomGenerator {
	public static long seq = 0;

	@Deprecated
	public static String get16Rand() {
		String str = MD5Utils.md5Digest(System.currentTimeMillis() + "" + (int) (Math.random() * 10000));
		return str.substring(0, 16).toUpperCase();
	}

	public static synchronized String genMerchantSeq(String merchantnum) {
		// String merchantNo = Configuration.getConfig(ConfigConstants.unps_merchantnum);
		String merchantNo = merchantnum;
		String dateTime = DateFormter.formatDateTime(new Date());

		int seqLength = 50 - merchantNo.length() - dateTime.length();
		String merchantSeq = merchantNo + String.format("%0" + seqLength + "d", seq) + dateTime; // 仅参考，商户自定义流水号生成
		seq++;
		return merchantSeq;
	}
}
