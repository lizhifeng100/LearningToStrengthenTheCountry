package com.lizhi;

import com.lizhi.service.Article;
import com.lizhi.service.Login;
import com.lizhi.service.SendMessage;
import com.lizhi.service.UrlRpc;
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

    //获取到token，可以6个小时的expire时间。
    //sign应该是不会过期的
    @Resource
    private Login login;

    @Resource
    private SendMessage sendMessage;

    @Resource
    private Article article;

    @Resource
    private UrlRpc rpc;

    //TODO "SCT90893TypZ3VHoKj1wVb4jwZNtdq5t3"
    @Test
    public void test1() {
        // login.login("SCT90893TypZ3VHoKj1wVb4jwZNtdq5t3");
        // rpc.readArticle();
    }
}
