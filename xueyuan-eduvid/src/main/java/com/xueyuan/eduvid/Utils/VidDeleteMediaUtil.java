package com.xueyuan.eduvid.Utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.vod.v20180717.VodClient;

import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;

public class VidDeleteMediaUtil {
    public static boolean remove(String FileId) {
        try {
            Credential cred = new Credential(ConstantDefineUtil.COS_SECRETID, ConstantDefineUtil.COS_SECRETKEY);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("vod.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            VodClient client = new VodClient(cred, "ap-chongqing", clientProfile);
            String params = "{\"FileId\":\"" + FileId + "\"}";
            DeleteMediaRequest req = DeleteMediaRequest.fromJsonString(params, DeleteMediaRequest.class);
            DeleteMediaResponse resp = client.DeleteMedia(req);
            return true;
        } catch (TencentCloudSDKException e) {
            return false;
        }
    }
}