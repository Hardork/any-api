package com.hwq.goatapiinterface.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hwq.goatapiclientsdk.exception.ApiException;
import com.hwq.goatapiclientsdk.model.params.*;
import com.hwq.goatapiclientsdk.model.response.RandomWallpaperResponse;
import com.hwq.goatapiclientsdk.model.response.ResultResponse;
import org.springframework.web.bind.annotation.*;
import com.hwq.goatapiinterface.utils.RequestUtils;

import java.util.Map;

import static com.hwq.goatapiinterface.utils.RequestUtils.buildUrl;
import static com.hwq.goatapiinterface.utils.ResponseUtils.baseResponse;
import static com.hwq.goatapiinterface.utils.ResponseUtils.responseToMap;
import static org.apache.el.util.MessageFactory.get;


/**
 * @Author: HWQ
 * @Date: 2023年08月16日 11:29
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class ServiceController {

    // 获取随机土味情话
    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        return RequestUtils.get("https://api.vvhan.com/api/love");
    }

    // 随机毒鸡汤
    @GetMapping("/poisonousChickenSoup")
    public String getPoisonousChickenSoup() {
        return RequestUtils.get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }

    // 随机壁纸
    @GetMapping("/randomWallpaper")
    public RandomWallpaperResponse randomWallpaper(RandomWallpaperParams randomWallpaperParams) throws ApiException {
        String baseUrl = "https://api.btstu.cn/sjbz/api.php";
        String url = buildUrl(baseUrl, randomWallpaperParams);
        if (StrUtil.isAllBlank(randomWallpaperParams.getLx(), randomWallpaperParams.getMethod())) {
            url = url + "?format=json";
        } else {
            url = url + "&format=json";
        }
        return JSONUtil.toBean(RequestUtils.get(url), RandomWallpaperResponse.class);
    }

    // 获取每日星座运势
    @GetMapping("/horoscope")
    public String getHoroscope(HoroscopeParams horoscopeParams) throws ApiException {
        return RequestUtils.get(buildUrl("https://api.vvhan.com/api/horoscope", horoscopeParams));
    }


    // 获取ip所属地
    @GetMapping("/ipInfo")
    public String getIpInfo(IpInfoParams ipInfoParams) {
        return RequestUtils.get(buildUrl("https://api.vvhan.com/api/getIpInfo", ipInfoParams));
    }

    // 获取天气
    @GetMapping("/weather")
    public String getWeatherInfo(WeatherParams weatherParams) {
        return RequestUtils.get(buildUrl("https://api.vvhan.com/api/weather", weatherParams));
    }

    // ai
    @GetMapping("/chatAi")
    public String getChatAI(ChatAIParams chatAIParams) {
        return RequestUtils.get(buildUrl("https://api.a20safe.com/api.php",chatAIParams));
    }

}