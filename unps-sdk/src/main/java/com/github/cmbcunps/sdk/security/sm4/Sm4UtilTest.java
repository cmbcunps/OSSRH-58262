package com.github.cmbcunps.sdk.security.sm4;
//package com.cmbc.unps.sdk.security.sm4;
//
//public class Sm4UtilTest {
//	public static void main(String[] args) {
//		try {
//			String json = "{\"name\":\"Marydon\",\"website\":\"http://www.cnblogs.com/Marydon20170307\"}";
//			// 自定义的32位16进制密钥
//			String key = "86C63180C2806ED1F47B859DE501215B";
//			String cipher = Sm4Util.encryptEcb(key, json);
//			System.out.println(cipher);// 05a087dc798bb0b3e80553e6a2e73c4ccc7651035ea056e43bea9d125806bf41c45b4263109c8770c48c5da3c6f32df444f88698c5c9fdb5b0055b8d042e3ac9d4e3f7cc67525139b64952a3508a7619
//			System.out.println(Sm4Util.verifyEcb(key, cipher, json));// true
//			json = Sm4Util.decryptEcb(key, cipher);
//			System.out.println(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
