package com.github.cmbcunps.sdk.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cmbcunps.sdk.constant.UnpsConstants;
import com.github.cmbcunps.sdk.exception.ErrorCodeEnum;
import com.github.cmbcunps.sdk.exception.PlatformException;
import com.github.cmbcunps.sdk.util.JsonHelper;

public class CMBCPostClient {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public Map<String, String> postCmbc(String requestUrl, Map<String, String> submitMap) throws PlatformException {
		return postCmbc(requestUrl, submitMap, UnpsConstants.isProxy, UnpsConstants.proxyAddress, UnpsConstants.proxyPort, UnpsConstants.connectTimeout, UnpsConstants.readTimeout);
	}

	private Map<String, String> postCmbc(String requestUrl, Map<String, String> submitMap, boolean isProxy, String proxyAddress, int proxyPort, int connectTimeout, int readTimeout)
			throws PlatformException {
		long start = System.currentTimeMillis();
		//
		String responseStr = "";
		HttpURLConnection conn = null;
		//
		OutputStream outputStream = null;
		PrintStream printStream = null;
		//
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			String requestStr = this.getParamStr(submitMap, UnpsConstants.ENCODING_UTF8);
			logger.info("requestUrl={}", requestUrl);
			logger.info("request={}", requestStr);
			//
			String protocol = requestUrl.substring(0, requestUrl.indexOf(":"));
			URL url = null;
			if ("https".equalsIgnoreCase(protocol)) {
				url = new URL(null, requestUrl, new sun.net.www.protocol.https.Handler());
			} else {
				url = new URL(null, requestUrl, new sun.net.www.protocol.http.Handler());
			}
			if (isProxy) {
				InetSocketAddress inetSocketAddress = new InetSocketAddress(proxyAddress, proxyPort);
				Proxy proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddress);
				conn = (HttpURLConnection) url.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setConnectTimeout(connectTimeout);// 连接超时时间
			conn.setReadTimeout(readTimeout);// 读取结果超时时间
			conn.setDoInput(true); // 可读
			conn.setDoOutput(true); // 可写
			conn.setUseCaches(false);// 取消缓存
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + UnpsConstants.ENCODING_UTF8);
			conn.setRequestMethod("POST");
			if ("https".equalsIgnoreCase(url.getProtocol())) {
				HttpsURLConnection httpsURLConnection = (HttpsURLConnection) conn;
				httpsURLConnection.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
				httpsURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());// 解决由于服务器证书问题导致HTTPS无法访问的情况
			}
			conn.connect();
			//
			outputStream = conn.getOutputStream();
			printStream = new PrintStream(outputStream, false, UnpsConstants.ENCODING_UTF8);
			printStream.print(requestStr);
			printStream.flush();
			//
			int status = conn.getResponseCode();
			if (UnpsConstants.status == status) {
				StringBuilder stringBuilder = new StringBuilder(4096);
				inputStream = conn.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream, UnpsConstants.ENCODING_UTF8);
				bufferedReader = new BufferedReader(inputStreamReader);
				String temp = null;
				while (null != (temp = bufferedReader.readLine())) {
					stringBuilder.append(temp);
				}
				responseStr = stringBuilder.toString();
			}
			logger.info("response={}", responseStr);
			return JsonHelper.jsonToMap(responseStr);
		} catch (IOException e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_003, e);
		} finally {
			logger.info("call CMBC UNPS used=" + (System.currentTimeMillis() - start));
			closeStream(outputStream, printStream, inputStream, inputStreamReader, bufferedReader);
			conn.disconnect();
		}
	}

	private void closeStream(OutputStream outputStream, PrintStream printStream, InputStream inputStream, InputStreamReader inputStreamReader, BufferedReader bufferedReader) {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (Exception e) {
		}
		try {
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
		} catch (Exception e) {
		}
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (Exception e) {
		}
		try {
			if (printStream != null) {
				printStream.close();
			}
		} catch (Exception e) {
		}
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (Exception e) {
		}
	}

	private String getParamStr(Map<String, String> submitMap, String encoding) throws UnsupportedEncodingException {
		if (encoding == null || "".equals(encoding)) {
			encoding = UnpsConstants.ENCODING_UTF8;
		}
		StringBuilder stringBuffer = new StringBuilder("");
		String reqstr = "";
		if (null != submitMap && submitMap.size() > 0) {
			for (Entry<String, String> keyMap : submitMap.entrySet()) {
				stringBuffer.append(keyMap.getKey()).append("=");
				stringBuffer.append(null == keyMap.getValue() || "".equals(keyMap.getValue()) ? "" : URLEncoder.encode(keyMap.getValue(), encoding));
				stringBuffer.append("&");
			}
			reqstr = stringBuffer.substring(0, stringBuffer.length() - 1);
		}
		return reqstr;
	}

}
