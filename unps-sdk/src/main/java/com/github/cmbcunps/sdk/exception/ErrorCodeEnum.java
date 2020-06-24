package com.github.cmbcunps.sdk.exception;

public enum ErrorCodeEnum {
	UNPS_CORE_001("UNPS_CORE_001", "加载配置文件失败"), //
	UNPS_CORE_002("UNPS_CORE_002", "加载证书失败"), //
	UNPS_CORE_003("UNPS_CORE_003", "创建http连接失败"), //
	UNPS_CORE_004("UNPS_CORE_004", "加签失败"), //
	UNPS_CORE_005("UNPS_CORE_005", "加密失败"), //
	UNPS_CORE_006("UNPS_CORE_006", "解密失败"), //
	UNPS_CORE_007("UNPS_CORE_007", "验签失败"), //
	UNPS_CORE_008("UNPS_CORE_008", "非法"), //
	UNPS_CORE_009("UNPS_CORE_009", "类型转换错误"), //
	UNPS_CORE_010("UNPS_CORE_010", "tokenId为空"), //
	UNPS_CORE_011("UNPS_CORE_011", "找不到商户安全集成方案"), //
	UNPS_CORE_012("UNPS_CORE_012", "读取文件失败"), //
	UNPS_CORE_013("UNPS_CORE_013", "写入文件失败"), //
	UNPS_CORE_014("UNPS_CORE_014", "下载文件失败"), //
	UNPS_CORE_015("UNPS_CORE_015", "上传文件失败"), //
	UNPS_CORE_016("UNPS_CORE_016", "解压文件失败"), //
	UNPS_CORE_017("UNPS_CORE_017", "压缩文件失败"), //
	UNPS_CORE_018("UNPS_CORE_018", "商户AES密钥配置有误， 请检查payment.ini文件。长度必须为16位"), //
	UNPS_CORE_019("UNPS_CORE_019", "RSA加密失败"), //
	UNPS_CORE_020("UNPS_CORE_020", "RSA解密失败"), //
	UNPS_CORE_021("UNPS_CORE_021", "{}"),//
	;

	private String errorCode;
	private String errorMsg;

	private ErrorCodeEnum(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	// private static HashMap<String, String> errorMsgMap = new HashMap<String, String>();
	//
	// static {
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_001, "加载配置文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_002, "加载证书失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_003, "创建http连接失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_004, "加签失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_005, "加密失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_006, "解密失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_007, "验签失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_008, "");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_009, "类型转换错误");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_010, "tokenId为空");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_011, "找不到商户安全集成方案");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_012, "读取文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_013, "写入文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_014, "下载文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_015, "上传文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_016, "解压文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_017, "压缩文件失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_018, "商户AES密钥配置有误， 请检查payment.ini文件。长度必须为16位");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_019, "RSA加密失败");
	// errorMsgMap.put(ErrorCodeEnum.UNPS_CORE_020, "RSA解密失败");
	// }

	// /** 加载配置文件失败 */
	// public static final String UNPS_CORE_001 = "UNPS_CORE_001";
	// /** 加载证书失败 */
	// public static final String UNPS_CORE_002 = "UNPS_CORE_002";
	// /** 创建http连接失败 */
	// public static final String UNPS_CORE_003 = "UNPS_CORE_003";
	// /** 加签失败 */
	// public static final String UNPS_CORE_004 = "UNPS_CORE_004";
	// /** 加密失败 */
	// public static final String UNPS_CORE_005 = "UNPS_CORE_005";
	// /** 解密失败 */
	// public static final String UNPS_CORE_006 = "UNPS_CORE_006";
	// /** 验签失败 */
	// public static final String UNPS_CORE_007 = "UNPS_CORE_007";
	// /** 保存文件失败 */
	// public static final String UNPS_CORE_008 = "UNPS_CORE_008";
	// /** 类型转换错误 */
	// public static final String UNPS_CORE_009 = "UNPS_CORE_009";
	// /** tokenId为空 */
	// public static final String UNPS_CORE_010 = "UNPS_CORE_010";
	// /** 找不到商户安全集成方案 */
	// public static final String UNPS_CORE_011 = "UNPS_CORE_011";
	// /** 读取文件失败 */
	// public static final String UNPS_CORE_012 = "UNPS_CORE_012";
	// /** 写入文件失败 */
	// public static final String UNPS_CORE_013 = "UNPS_CORE_013";
	// /** 下载文件失败 */
	// public static final String UNPS_CORE_014 = "UNPS_CORE_014";
	// /** 上传文件失败 */
	// public static final String UNPS_CORE_015 = "UNPS_CORE_015";
	// /** 解压文件失败 */
	// public static final String UNPS_CORE_016 = "UNPS_CORE_016";
	// /** 压缩文件失败 */
	// public static final String UNPS_CORE_017 = "UNPS_CORE_017";
	// /** 商户AES密钥配置有误， 请检查payment.ini文件。长度必须为16位 */
	// public static final String UNPS_CORE_018 = "UNPS_CORE_018";
	// /** RSA加密失败 */
	// public static final String UNPS_CORE_019 = "UNPS_CORE_019";
	// /** RSA解密失败 */
	// public static final String UNPS_CORE_020 = "UNPS_CORE_020";
	//
	// public static String getErrorMsg(String errorCode) {
	// return errorMsgMap.get(errorCode);
	// }
}
