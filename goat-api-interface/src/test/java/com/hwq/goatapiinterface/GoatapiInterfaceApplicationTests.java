package com.hwq.goatapiinterface;

import com.hwq.goatapiclientsdk.client.GoatApiClient;
import com.hwq.goatapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GoatapiInterfaceApplicationTests {

    @Resource
    private GoatApiClient goatApiClient;
    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("hwq");
        String userNameByPost = goatApiClient.getUserNameByPost(user);
        System.out.println(userNameByPost);
    }

}
