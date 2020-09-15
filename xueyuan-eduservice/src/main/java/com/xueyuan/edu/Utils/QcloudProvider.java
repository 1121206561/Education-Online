package com.xueyuan.edu.Utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class QcloudProvider {

    private String COS_SECRETID = ConstantDefineUtil.COS_SECRETID;

    private String COS_SECRETKEY = ConstantDefineUtil.COS_SECRETKEY;

    private String COS_REGION = ConstantDefineUtil.COS_REGION;

    private String BucketName = ConstantDefineUtil.BUCKETNAME;

    public String FileToCos(InputStream file, String host) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = COS_SECRETID;
        String secretKey = COS_SECRETKEY;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(COS_REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传的文件;
        // 指定要上传到的存储桶
        String bucketName = BucketName;
        // 指定要上传到 COS 上对象键
        String fileName = UUID.randomUUID().toString() + ".png";
        //代码优化 存储桶加上日期文件夹 每天的图片存储在当月的文件夹内
        if (StringUtils.isEmpty(host)) {
            host = "head";
        }
        String key = host + "/" + new DateTime().toString("yyyy/MM/dd") + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file, new ObjectMetadata());
        cosClient.putObject(putObjectRequest);
        //获取预签名url
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60L * 1000L * 1000 * 100);
        req.setExpiration(expirationDate);
        URL url = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();
        return url.toString();
    }
}
