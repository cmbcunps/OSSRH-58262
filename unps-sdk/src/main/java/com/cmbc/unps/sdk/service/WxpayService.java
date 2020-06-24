package com.cmbc.unps.sdk.service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cmbc.unps.sdk.config.ConfigConstants;
import com.cmbc.unps.sdk.config.Configuration;
import com.cmbc.unps.sdk.exception.ErrorCodeEnum;
import com.cmbc.unps.sdk.exception.PlatformException;
import com.cmbc.unps.sdk.util.JsonHelper;
import com.github.wxpay.sdk.MyWXPayConfig;
import com.github.wxpay.sdk.WXPay;

public class WxpayService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//
	private CmbcFileService postFileService = null;;

	public WxpayService(CmbcFileService postFileService) {
		this.postFileService = postFileService;
	}

	public Map<String, Object> postWxpayBillToCMBC(Map<String, String> requestMap, String requestUrl, String billDate) throws PlatformException {
		String filePath_tmp = Configuration.getConfig(ConfigConstants.filePath_tmp);
		String fileFullName = filePath_tmp + "wxpay_" + billDate + ".txt";
		File file = new File(fileFullName);
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			// 入参校验
			// if (null == billDate || billDate.length() != 8) {
			// throw new Exception("账单日期为空或格式不合法，格式为yyyyMMdd,您的输入为:" + billDate);
			// }
			// if (StringUtils.isEmpty(contractid) || contractid.length() != 20)
			// {
			// throw new Exception("签约编码格式不合法");
			// }
			//
			MyWXPayConfig myWXPayConfig = new MyWXPayConfig();
			myWXPayConfig.setAppID(Configuration.getConfig("unps.wxpayappid"));
			myWXPayConfig.setMchID(Configuration.getConfig("unps.wxpaymchtid"));
			myWXPayConfig.setKey(Configuration.getConfig("unps.wxpaykey"));
			WXPay wxPay = new WXPay(myWXPayConfig);
			//
			Map<String, String> data = new HashMap<String, String>();
			data.put("bill_date", billDate);
			// data.put("tar_type", "GZIP");
			data.put("bill_type", "ALL");
			Map<String, String> resp = wxPay.downloadBill(data);
			logger.error(JSON.toJSONString(resp));
			if ("SUCCESS".equals(resp.get("return_code"))) {
				fw = new FileWriter(file);
				pw = new PrintWriter(fw);
				pw.println(resp.get("data"));
				fw.flush();
				pw.flush();
			} else {
				throw new PlatformException(ErrorCodeEnum.UNPS_CORE_021, JsonHelper.objectToJson(resp));
			}
		} catch (Exception e) {
			logger.error("download wxpay bill error,{}", e);
			if (file.exists()) {
				file.delete();
			}
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_014, e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//
		File file1 = new File(fileFullName);
		// return postFileService.postFileToCMBC(requestMap, file1, true);
		return postFileService.postFileToCMBC(requestMap, requestUrl, file1);
	}

}
