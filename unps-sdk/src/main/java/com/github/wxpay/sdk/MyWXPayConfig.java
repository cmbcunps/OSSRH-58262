package com.github.wxpay.sdk;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.cmbc.unps.sdk.config.Configuration;

public class MyWXPayConfig extends WXPayConfig {

	private byte[] certData;

	public MyWXPayConfig() throws Exception {
		// CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
		// cmbcUnpsAgent.loadConfigAndCert();
		String p12 = Configuration.getConfig("unps.wxpayp12");
		String certPath = p12;
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();
	}

	/* 获取 App ID */
	public String AppID;
	/* 获取 Mch ID */
	public String MchID;
	/* 获取 API 密钥 */
	public String Key;

	public void setAppID(String appID) {
		AppID = appID;
	}

	public void setMchID(String mchID) {
		MchID = mchID;
	}

	public void setKey(String key) {
		Key = key;
	}

	/**
	 * 获取 App ID
	 *
	 * @return App ID
	 */
	public String getAppID() {
		return AppID;
	}

	/**
	 * 获取 Mch ID
	 *
	 * @return Mch ID
	 */
	public String getMchID() {
		return MchID;
	}

	/**
	 * 获取 API 密钥
	 *
	 * @return API密钥
	 */
	public String getKey() {
		return Key;
	}

	/**
	 * 获取商户证书内容
	 *
	 * @return 商户证书内容
	 */
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	/**
	 * HTTP(S) 连接超时时间，单位毫秒
	 *
	 * @return
	 */
	public int getHttpConnectTimeoutMs() {
		return 6 * 1000;
	}

	/**
	 * HTTP(S) 读数据超时时间，单位毫秒
	 *
	 * @return
	 */
	public int getHttpReadTimeoutMs() {
		return 8 * 1000;
	}

	/**
	 * 获取WXPayDomain, 用于多域名容灾自动切换
	 * 
	 * @return
	 */
	public IWXPayDomain getWXPayDomain() {
		return MyWXPayDomainSimpleImpl.instance();
	}

	/**
	 * 是否自动上报。 若要关闭自动上报，子类中实现该函数返回 false 即可。
	 *
	 * @return
	 */
	public boolean shouldAutoReport() {
		return true;
	}

	/**
	 * 进行健康上报的线程的数量
	 *
	 * @return
	 */
	public int getReportWorkerNum() {
		return 6;
	}

	/**
	 * 健康上报缓存消息的最大数量。会有线程去独立上报 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
	 *
	 * @return
	 */
	public int getReportQueueMaxSize() {
		return 10000;
	}

	/**
	 * 批量上报，一次最多上报多个数据
	 *
	 * @return
	 */
	public int getReportBatchSize() {
		return 10;
	}

}