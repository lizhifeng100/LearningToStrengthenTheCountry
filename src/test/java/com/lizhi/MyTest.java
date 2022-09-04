package com.lizhi;

import com.lizhi.service.Login;
import com.lizhi.service.SendMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: 荔枝
 * @date: 2022/9/3 22 10
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DockerLMCApp.class)
public class MyTest {

    @Resource
    private Login login;

    @Resource
    private SendMessage sendMessage;

    //TODO "SCT90893TypZ3VHoKj1wVb4jwZNtdq5t3"
    @Test
    public void test1() {
        login.login("SCT90893TypZ3VHoKj1wVb4jwZNtdq5t3");
    }
}
