package com.hwq.goatapiinterface.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;

/**
 * @Author:HWQ
 * @DateTime:2023/7/17 10:44
 * @Description:
 **/
public class SignUtil {
    public static String getSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String content = body.toString() + "." + secretKey;
        return md5.digestHex(content);
    }
}
