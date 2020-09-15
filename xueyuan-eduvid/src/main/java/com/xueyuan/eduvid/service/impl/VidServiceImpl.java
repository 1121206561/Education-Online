package com.xueyuan.eduvid.service.impl;

import com.xueyuan.eduvid.Utils.ConstantDefineUtil;
import com.xueyuan.eduvid.Utils.VidDeleteMediaUtil;
import com.xueyuan.eduvid.eneity.Signature;
import com.xueyuan.eduvid.service.VidService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VidServiceImpl implements VidService {

    @Override
    public String getSignature() {
        //得到Sign
        Signature sign = new Signature();
        //个人API密钥中的Secret Id
        sign.setSecretId(ConstantDefineUtil.COS_SECRETID);
        //个人API密钥中的Secret Key
        sign.setSecretKey(ConstantDefineUtil.COS_SECRETKEY);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2);

        String signature = null;
        try {
            signature = sign.getUploadSignature();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    @Override
    public Boolean remove(String videoSourceId) {
        return VidDeleteMediaUtil.remove(videoSourceId);
    }
}
