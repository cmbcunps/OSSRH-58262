package other;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.cmbcunps.sdk.config.ConfigConstants;
import com.github.cmbcunps.sdk.config.Configuration;
import com.github.cmbcunps.sdk.http.CMBCPostClient;
import com.github.cmbcunps.sdk.security.base.AESUtils;
import com.github.cmbcunps.sdk.security.base.RSAUtil;
import com.github.cmbcunps.sdk.util.JsonHelper;
import com.github.cmbcunps.sdk.util.UnpsUtil;

@Deprecated
public class CMBCInnerService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Map<String, String> postKey(Map<String, String> requestMap) throws Exception {
		String requestUrl = Configuration.getConfig(ConfigConstants.unps_transurl);
		return postCMBC(requestMap, requestUrl);
	}

	public Map<String, String> postKey(Map<String, String> requestMap, String requestUrl) throws Exception {
		return postCMBC(requestMap, requestUrl);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public Map<String, String> postCMBC(Map<String, String> requestMap, String requestUrl) throws Exception {
		Map<String, String> submitMap = new HashMap<String, String>();
		Map<String, String> busiMap = new HashMap<String, String>();
		Map<String, String> responseMap = new HashMap<String, String>();
		Map<String, Object> responseBusiMap = new HashMap<String, Object>();

		String randCipher1 = (String) requestMap.get("randCipher");

		UnpsUtil.splidReqMap(requestMap, submitMap, busiMap);
		// RSA签名方式
		String randCipher_enc = RSAUtil.encrypt(JsonHelper.objectToJson(busiMap));
		submitMap.put("businessContext", randCipher_enc);

		logger.info("requestUrl:" + requestUrl);
		logger.info("submitMap:" + JSON.toJSONString(submitMap));

		CMBCPostClient client = new CMBCPostClient();
		responseMap = client.postCmbc(requestUrl, submitMap);
		logger.info("responseMap:" + JSON.toJSONString(responseMap));

		String businessContext = (String) responseMap.get("businessContext");
		String businessContext_dec = AESUtils.decrypt(businessContext, randCipher1.getBytes());

		responseBusiMap = JSON.parseObject(businessContext_dec, Map.class);
		return responseMap;
	}

}
