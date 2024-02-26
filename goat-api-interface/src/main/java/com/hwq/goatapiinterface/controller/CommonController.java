package com.hwq.goatapiinterface.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hwq.goatapiinterface.model.request.CodeRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:HWQ
 * @DateTime:2023/7/29 13:40
 * @Description:
 **/
@RestController
@RequestMapping("/common")
@CrossOrigin
public class CommonController {
    /**
     * 返回随机数字的验证码
     * @param codeRequest 验证码长度
     * @return
     */
    @PostMapping("/code")
    public String getCode(@RequestBody CodeRequest codeRequest) {
        if (codeRequest == null || codeRequest.getLimit() == null || codeRequest.getLimit() <= 0 || codeRequest.getLimit() > 100) {
            throw new RuntimeException("参数错误");
        }
        return RandomUtil.randomNumbers(codeRequest.getLimit());
    }

    @GetMapping("/image")
    public Object getImage() {
        String res = HttpUtil.get("https://api.wrdan.com/randimg?key=mm");
        JSONObject jsonObject = JSONUtil.parseObj(res);
        Object imgUrl = jsonObject.get("imgUrl");
        return imgUrl;
    }
}
