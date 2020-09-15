package com.xueyuan.eduvid.service;

public interface VidService {
    String getSignature();

    Boolean remove(String videoSourceId);
}
