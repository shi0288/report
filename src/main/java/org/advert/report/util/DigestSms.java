package org.advert.report.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;

/**
 * Created by bjjg11 on 2014/8/11.
 */

public class DigestSms {

    public static boolean SendMsg(String mobile, String msgCode) {

        HashMap<String, Object> result = null;

        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(SystemConstant.MSG_URL, SystemConstant.MSG_PORT);
        restAPI.setAccount(SystemConstant.MSG_ACOUNT_SID, SystemConstant.MSG_AUTH_TOKEN);
        restAPI.setAppId(SystemConstant.MSG_APP_ID);
        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        result = restAPI.sendTemplateSMS(mobile, SystemConstant.MSG_ID, new String[]{msgCode});
        if ("000000".equals(result.get("statusCode"))) {
            return true;
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            return false;

        }
    }

}
