package com.hwq.project;

import com.hwq.project.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserCenterApplicationTests {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    

    @Test
    public void testSelect() {
        userInterfaceInfoService.invoke(24,1);
    }

    @Test
    public void testRedis() {

    }

}
