package org.advert.report.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shiqm on 2018-06-13.
 */
public class HttpClientWrapper {

    /* 请求超时时间 */
    private static int Request_TimeOut = 20000;
    /* 当前连接池中来年接诶 */
    private static CloseableHttpClient httpClientCur = null;

    private synchronized static CloseableHttpClient getHttpClient() {
        if (httpClientCur == null) {
             /* Http连接池只需要创建一个*/
            PoolingHttpClientConnectionManager httpPoolManager = new PoolingHttpClientConnectionManager();
                /* 连接池最大生成连接数200 */
            httpPoolManager.setMaxTotal(200);
              /* 连接池默认路由最大连接数,默认为20 */
            httpPoolManager.setDefaultMaxPerRoute(20);

            //ConnectionConfig
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(Request_TimeOut)
                    .setConnectionRequestTimeout(Request_TimeOut)
                    .setSocketTimeout(Request_TimeOut)
                    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(
                            Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .build();

//            HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
//                if (executionCount >= 3) {
//                    // 超过三次则不再重试请求
//                    return false;
//                }
//                if (exception instanceof InterruptedIOException) {
//                    // Timeout
//                    return false;
//                }
//                if (exception instanceof UnknownHostException) {
//                    // Unknown host
//                    return false;
//                }
//                if (exception instanceof ConnectTimeoutException) {
//                    // Connection refused
//                    return false;
//                }
//                if (exception instanceof SSLException) {
//                    // SSL handshake exception
//                    return false;
//                }
//                HttpClientContext clientContext = HttpClientContext.adapt(context);
//                HttpRequest request = clientContext.getRequest();
//                if (!(request instanceof HttpEntityEnclosingRequest)) {
//                    return true;
//                }
//                return false;
//            };
            httpClientCur = HttpClients.custom()
                    .setConnectionManager(httpPoolManager)
                    .setDefaultRequestConfig(defaultRequestConfig)
//                    .setRetryHandler(httpRequestRetryHandler)
                    .build();
        }
        return httpClientCur;
    }



    public static String login()  {
        HttpPost request = new HttpPost(Cons.LOGIN_URL);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("loginCode", "qiaoshao");
        jsonParam.put("loginPwd", "123456");
        StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        request.setEntity(entity);
        HttpContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(request, context);
            Header[] headers = response.getAllHeaders();
            if (headers != null && headers.length > 0) {
                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    if (header.getName().trim().toUpperCase().equals("Set-Cookie".toUpperCase())) {
                        return Cons.COOKIES+header.getValue().split(";")[0];
                    }
                }
            }
            return null;
        }catch (Exception e){
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static String query(String time,String cookies) {
        HttpPost request = new HttpPost(Cons.QUERY_URL);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
        request.setHeader("Cookie", cookies);
        List<NameValuePair> reqParams = new ArrayList<NameValuePair>();
        reqParams.add(new BasicNameValuePair("page", "1"));
        reqParams.add(new BasicNameValuePair("rows", "50"));
        reqParams.add(new BasicNameValuePair("beginDate", time));
        reqParams.add(new BasicNameValuePair("endDate", time));
        request.setEntity(new UrlEncodedFormEntity(reqParams, Consts.UTF_8));
        HttpContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(request, context);
            String retString = EntityUtils.toString(response.getEntity());
            return retString;
        }catch (Exception e){
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
