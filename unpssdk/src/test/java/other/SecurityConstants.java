package other;

public final class SecurityConstants {

	public static final String SEC_APP_DEF = "0";
	public static final String SEC_CHARSET = "UTF-8";
	public static final String SEC_KEY_RANDOM = "random";
	public static final String SEC_KEY_TMPKEY = "tmpKey";
	public static final String SEC_KEY_SECKEY = "secKey";
	public static final String SEC_KEY_IV = "iv";
	public static final String SEC_ENCRYPT_TYPE = "encryptType";
	public static final String SEC_KEY_CHK_FIELD = "checkField";
	public static final String SEC_KEY_CHK_TYPE = "checkType";
	public static final String SEC_KEY_CHK_SCALE = "checkScale";
	public static final String SEC_KEY_CHK_FLAG = "checkFlag";
	public static final int SEC_KEY_SCALE_ACCT = 0; // 借记卡
	public static final int SEC_KEY_SCALE_CCACCT = 1; // 信用卡
	public static final int SEC_KEY_SCALE_AUTHACCT = 2; // 授权账户
	public static final int SEC_KEY_SCALE_EACCT = 3; // 电子账户
	public static final int SEC_KEY_SCALE_OBACCT = 4; // 他行账户
	public static final String SEC_FLAG_TRUE = "1";
	public static final String SEC_FLAG_FLASE = "0";
	public static final int SEC_CFG_RANDOM_LENGTH = 8;
	public static final String SEC_FUNC_TMPKEY = "getRandom";
	public static final String SEC_MOD_AES_ECB_PKCS5 = "AES/ECB/PKCS5Padding";
	public static final String SEC_MOD_AES_CBC_PKCS7 = "AES/CBC/PKCS7Padding";
	public static final String SEC_INJECTION_STRING = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|\\*|%|;|-|--|\\+|,|like|//|/|%|#| |=)\\b)";

	public static enum EncryptType {
		NONE, AES, RSA, SM2, MD5, SHA1, DES
	}

	public static enum CheckType {
		USERID, CUSTID, MOBILE, ACCT, IDNO
	}

	public static enum Algorithm {
		MD5withRSA
	}
}
