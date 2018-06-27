
package org.advert.report.util;


/**
 * 系统常量
 * 
 * @author w44
 * 
 */
public class SystemConstant {



	/**
	 *  短信模板ID
	 */
	public static final String MSG_ID = "255272";



	/**
	 *  短信地址及端口
	 */
	//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
	//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
	public static final String MSG_URL = "app.cloopen.com";
	public static final String MSG_PORT = "8883";

	/**
	 *  短信平台账户ACOUNT SID，AUTH TOKEN
	 */
	public static final String MSG_ACOUNT_SID = "8a48b5514f73ea32014f8896e1722ab5";
	public static final String MSG_AUTH_TOKEN = "b04b21a1dc6a4cda825982b7e57a995c";

	/**
	 *  短信平台账户APP ID
	 */
	public static final String MSG_APP_ID = "8a48b5514f73ea32014f8898c93d2abe";

}
