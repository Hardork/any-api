package com.hwq.goatapiinterface.controller;

/**
 * @Author:HWQ
 * @DateTime:2023/7/16 19:44
 * @Description:
 **/

import com.hwq.goatapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称API
 */
@RestController
@RequestMapping("/name")
@CrossOrigin
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "GET你的名字是:" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "Posdasdjasdt你的名字是:" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user) {
        String res = user.getUsername();
        // 调用次数 +1
        return res;
    }
}
