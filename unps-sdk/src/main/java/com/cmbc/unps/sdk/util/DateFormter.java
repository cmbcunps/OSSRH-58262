package com.cmbc.unps.sdk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormter {

	private static final ThreadLocal<SimpleDateFormat> dateFormater_yyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		};
	};

	public static String formatDate(Date date) {
		return dateFormater_yyyyMMdd.get().format(date);
	}

	private static final ThreadLocal<SimpleDateFormat> dateFormater_HHmmssSSS = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HHmmssSSS");
		};
	};

	public static String formatTime(Date date) {
		return dateFormater_HHmmssSSS.get().format(date);
	}

	private static final ThreadLocal<SimpleDateFormat> dateFormater_yyyyMMddHHmmssSSS = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		};
	};

	public static String formatDateTime(Date date) {
		return dateFormater_yyyyMMddHHmmssSSS.get().format(date);
	}
}
