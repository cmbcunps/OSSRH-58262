package com.cmbc.unps.sdk.cert;

import java.util.ArrayList;
import java.util.List;

import com.cmbc.unps.sdk.config.ConfigConstants;
import com.cmbc.unps.sdk.config.Configuration;
import com.cmbc.unps.sdk.exception.ErrorCodeEnum;
import com.cmbc.unps.sdk.exception.PlatformException;
import com.cmbc.unps.sdk.util.TxtFileUtil;
import com.cmbc.unps.sdk.util.UnpsUtil;

public class CertLoader {

	public static boolean init(String merchantNum) throws PlatformException {
		try {
			// merchant
			// String merchantNum = Configuration.getConfig(ConfigConstants.unps_merchantnum);
			String merCertPath = Configuration.getConfig(ConfigConstants.unps_custcertpath);
			String merSm2Path = Configuration.getConfig(ConfigConstants.unps_custsm2path);
			String merSm2Password = Configuration.getConfig(ConfigConstants.unps_custsm2pass);
			UnpsCert merchantCert = loadMerchantCert(merchantNum, merCertPath, merSm2Path, merSm2Password);
			// cmbc
			String cmbcCertPath = Configuration.getConfig(ConfigConstants.unps_cmbccertpath);
			String cmbcSm2Path = Configuration.getConfig(ConfigConstants.unps_cmbcsm2path);
			String cmbcSm2Password = Configuration.getConfig(ConfigConstants.unps_cmbcsm2pass);
			UnpsCert cmbcCert = CertLoader.loadCmbcCert(cmbcCertPath, cmbcSm2Path, cmbcSm2Password);
			//
			merchantCert.cmbcCer = cmbcCert.cmbcCer;
			merchantCert.cmbcPublicKey = cmbcCert.cmbcPublicKey;
			merchantCert.cmbcSm2 = cmbcCert.cmbcSm2;
			merchantCert.cmbcPrivateKey = cmbcCert.cmbcPrivateKey;
			merchantCert.cmbcSm2Password = cmbcCert.cmbcSm2Password;
			//
			CertConfig.addDefaultMerchantCert(merchantCert);
			return true;
		} catch (PlatformException e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_002, e);
		}
	}

	public static UnpsCert loadMerchantCert(String merchantNum, String certPath, String sm2Path, String sm2Password) throws PlatformException {
		UnpsCert merchantCert = new UnpsCert();
		try {
			merchantCert.merchantNum = merchantNum;
			//
			merchantCert.merCer = UnpsCertUtil.loadCert(certPath);
			merchantCert.merPublicKey = merchantCert.merCer.getPublicKey();
			//
			if (sm2Path != null && sm2Password != null) {
				merchantCert.merSm2 = UnpsCertUtil.loadSm2(sm2Path);
				merchantCert.merPrivateKey = UnpsCertUtil.loadSm2PrivateKey(sm2Path, sm2Password);
				merchantCert.merSm2Password = sm2Password;
			}
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_002, e);
		}
		return merchantCert;
	}

	public static UnpsCert loadCmbcCert(String cmbcCertPath, String cmbcSm2Path, String cmbcSm2Password) throws PlatformException {
		UnpsCert cmbcCert = new UnpsCert();
		try {
			cmbcCert.merchantNum = "cmbc";
			//
			cmbcCert.cmbcCer = UnpsCertUtil.loadCert(cmbcCertPath);
			cmbcCert.cmbcPublicKey = cmbcCert.cmbcCer.getPublicKey();
			//
			if (!UnpsUtil.isBlank(cmbcSm2Path) && !UnpsUtil.isBlank(cmbcSm2Password)) {
				cmbcCert.cmbcSm2 = UnpsCertUtil.loadSm2(cmbcSm2Path);
				cmbcCert.cmbcPrivateKey = UnpsCertUtil.loadSm2PrivateKey(cmbcSm2Path, cmbcSm2Password);
				cmbcCert.cmbcSm2Password = cmbcSm2Password;
			}
		} catch (Exception e) {
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_002, e);
		}
		return cmbcCert;
	}

	public static boolean saveCustSm2Pass(String filePath, String fileName) throws PlatformException {
		try {
			List<String> strList = new ArrayList<String>();
			String str = null;
			for (UnpsCert cert : CertConfig.merchantCertMap.values()) {
				str = cert.merchantNum + "=" + cert.merSm2Password;
				strList.add(str);
			}
			TxtFileUtil.reCreatFile(filePath, fileName);
			return TxtFileUtil.writeToTxt(strList, filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(ErrorCodeEnum.UNPS_CORE_013, e);
		}
	}

	public static List<String> listCerts() {
		List<String> strList = new ArrayList<String>();
		for (UnpsCert cert : CertConfig.merchantCertMap.values()) {
			strList.add(cert.merchantNum);
		}
		return strList;
	}

}
