package test;

import java.io.File;

import com.github.cmbcunps.sdk.util.ZipUtil;

public class ZipUtilTest {

	public static void main(String[] args) throws Exception {
		File srcFile = new File("/mskj/unps-sdk-demo/tmp/alipay_2019-06-20.zip");
		File tarFile = new File("/mskj/unps-sdk-demo/tmp/alipay_2019-06-20");
		File reconFile = new File("/mskj/unps-sdk-demo/tmp/recon.zip");
		String entryName = "alipay_2019-06-20";
		//
		ZipUtil.unzip(srcFile, tarFile);
		//
		ZipUtil.zip(tarFile, reconFile, entryName);
	}
}
