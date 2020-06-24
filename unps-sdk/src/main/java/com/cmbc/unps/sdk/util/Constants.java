package com.cmbc.unps.sdk.util;

public class Constants {
	public static final String SECU_RULE_SM201 = "SM201"; // SM2仅加签
	public static final String SECU_RULE_SM202 = "SM202"; // SM2仅加密
	public static final String SECU_RULE_SM203 = "SM203"; // SM2加密加签

	public static final String PAY_T001 = "PAY_T001";
	public static final String PAY_T002 = "PAY_T002";

	public static final String body = "body";
	public static final String sign = "sign";
	public static final String inateMerchSign = "inateMerchSign";

	public static final String cmbcUrl = "cmbcUrl";
	// =================================================公共参数
	public static final String version = "version";// 版本号
	public static final String source = "source";// 发起来源
	public static final String merchantNum = "merchantNum";// 商户编号
	public static final String merchantSeq = "merchantSeq";// 商户流水
	public static final String transDate = "transDate";// 交易日期
	public static final String transTime = "transTime";// 交易时间
	public static final String transCode = "transCode";// 交易编码
	public static final String securityType = "securityType";// 安全类型
	public static final String reserveJson = "reserveJson";// 通用备用字段
	public static final String businessContext = "businessContext";// 业务报文

	// public static final String merchantSeq ="merchantSeq";"// 商户流水
	// public static final String transCode ="transCode";"// 交易编码
	public static final String gateSeq = "gateSeq";// 网关响应流水
	public static final String gateTransDate = "gateTransDate";// 网关响应日期
	public static final String gateTransTime = "gateTransTime";// 网关响应时间
	public static final String gateReturnType = "gateReturnType";// 网关响应结果
	public static final String gateReturnCode = "gateReturnCode";// 网关响应错误码
	public static final String gateReturnMessage = "gateReturnMessage";// 网关响应错误信息
	public static final String reserve1 = "reserve1";// 备用字段1
	public static final String reserve2 = "reserve2";// 备用字段2
	public static final String reserve3 = "reserve3";// 备用字段3
	// public static final String reserveJson ="reserveJson";"// 通用备用字段
	// public static final String businessContext ="businessContext";"// 业务报文

	public static final String HAED_PARAMS[] = { "version", "source", "merchantNum", "merchantSeq", "transDate", "transTime", "transCode", "securityType", "reserve1", "reserve2",
			"reserve3", "reserve4", "reserve5", "reserveJson" };
	// =================================================业务参数
	public static final String defaultTradeType = "defaultTradeType";
	public static final String orgMerchantSeq = "orgMerchantSeq";
	public static final String busiType = "busiType";
	public static final String busiCode = "busiCode";
	public static final String busiMsg = "busiMsg";
	public static final String reply = "reply";
	public static final String returnCode = "returnCode";
	public static final String outputHead = "outputHead";
	public static final String batchList = "batchList";
	public static final String pageInfo = "pageInfo";
	public static final String type = "type";
	public static final String code = "code";
	public static final String message = "message";
	public static final String prdTransTime = "prdTransTime";
	public static final String prdSysCode = "prdSysCode";
	public static final String prdTransNo = "prdTransNo";
	public static final String status = "status";
	public static final String resultFileName = "resultFileName";
	public static final String flag = "flag"; // 操作标示"I-新增U-修改"
	public static final String mchtId = "mchtId"; // 商户号
	public static final String attrBranch = "attrBranch"; // 归属分支机构 一二级分行
	public static final String servId = "servId"; // 产品编号
	public static final String contractId = "contractId"; // 签约编码
	public static final String inaterContractId = "inaterContractId"; // 二级签约编号"若为一级商户白名单则填写一级商户签约编码"
	public static final String draweeAccNo = "draweeAccNo"; // 付款人帐号
	public static final String draweeAccName = "draweeAccName"; // 付款人名称
	public static final String draweePartyId = "draweePartyId"; // 付款账户开户行
	public static final String payeeAccNo = "payeeAccNo"; // 收款人账号
	public static final String payeeAccName = "payeeAccName"; // 收款人名称
	public static final String payeePartyId = "payeePartyId"; // 收款账户开户行
	public static final String certType = "certType"; // 证件类型
	public static final String certNo = "certNo"; // 证件号码
	public static final String phoneId = "phoneId"; // 电话号码
	public static final String startDate = "startDate"; // 开始日期
	public static final String endDate = "endDate"; // 结束日期
	public static final String lmtAmt = "lmtAmt"; // 限额
	public static final String acctType = "acctType"; // 账户类型"0-对公1-卡2-折"
	public static final String ctrlFlag = "ctrlFlag"; // 黑白名单类型"（从左至右）第一位：1――允许（白名单）2――限制（黑名单）第二位(由于‘交易行为’只能是付款、收款中的一种。‘受限类型’只需指定‘允许、限制’即可。目前，暂不使用，可根据实际情况，进行调整)：0――所有收付1――付款2――收款"
	public static final String stat = "stat"; // 状态"1-启用2-注销"
	public static final String generalField = "generalField"; // 通用域
	public static final String remark1 = "remark1"; // 预留字段1
	public static final String remark2 = "remark2"; // 预留字段2
	public static final String remark3 = "remark3"; // 预留字段3
	public static final String protocolId = "protocolId";// 客户协议编号
	public static final String fileName = "fileName"; // 文件名
	public static final String fileType = "fileType"; // 文件类型
	public static final String batchId = "batchId"; // 批次号
	public static final String beginPage = "beginPage"; // 起始页
	public static final String pageAmount = "pageAmount"; // 页大小
	public static final String postscript = "postscript";// 摘要
	public static final String summary = "summary";// 备注
	public static final String fileContent = "fileContent";
	public static final String settTime = "settTime";
	public static final String fileSize = "fileSize";
	public static final String endFlag = "endFlag";

	public static final String segmentIndex = "segmentIndex";
	public static final String segmentBase64Str = "segmentBase64Str";
	public static final String segmentLength = "segmentLength";
	public static final String currentSegmentContentBase64String = "currentSegmentContentBase64String";
	public static final String fileAllSegments = "fileAllSegments";
	public static final String localFile = "localFile";
	public static String acctUse = "acctUse";
	public static String startAmount = "startAmount";
	public static String endAmount = "endAmount";
	public static String billDate = "billDate";
}