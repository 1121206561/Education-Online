package com.xueyuan.edu.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtil {
    /**
     * 发送POST请求
     *
     * @param url    请求的接口路径
     * @param params 参数
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        CloseableHttpClient client = createSSLClientDefault();
        StringBuilder stringBuilder = new StringBuilder(url);
        //第一个参数
        stringBuilder.append("?client_id=");
        stringBuilder.append(params.get("client_id"));
        //第二个参数
        stringBuilder.append("&client_secret=");
        stringBuilder.append(params.get("client_secret"));
        //第三个参数
        stringBuilder.append("&grant_type=");
        stringBuilder.append(params.get("grant_type"));
        //第四个参数
        stringBuilder.append("&code=");
        stringBuilder.append(params.get("code"));
        //第五个参数
        stringBuilder.append("&redirect_uri=");
        stringBuilder.append(params.get("redirect_uri"));
        HttpPost httpPost = new HttpPost(stringBuilder.toString());
        //发送请求返回响应的信息
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }
        return null;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
            //有效的SSL会话并匹配到目标主机。
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    public static String get(String url, Object access_token, Object uid) throws IOException {
        CloseableHttpClient client = createSSLClientDefault();
        StringBuilder stringBuilder = new StringBuilder(url);
        //第一个参数
        stringBuilder.append("?access_token=");
        stringBuilder.append(access_token);
        //第二个参数
        stringBuilder.append("&uid=");
        stringBuilder.append(uid);
        HttpGet httpGet = new HttpGet(stringBuilder.toString());
        //发送请求返回响应的信息
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }
        return null;
    }

}
