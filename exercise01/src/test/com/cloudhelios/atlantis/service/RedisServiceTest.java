package com.cloudhelios.atlantis.service;

import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.util.JsonUtil;
import com.cloudhelios.atlantis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;

/**
 * author: chenwei
 * createDate: 18-8-30 下午3:03
 * description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void testSet() {
        AtlUser atlUser = new AtlUser();
        atlUser.setId(10000l);
        atlUser.setUsername("饕餮");
        atlUser.setEmail("yan.h.h@qq.com");
        atlUser.setPassword("111111");
        atlUser.setPhone("15968213258");
        atlUser.setEmployeeId("10000");
//        String json = JsonUtil.toJson(atlUser);

        redisUtil.set("USER:" + atlUser.getId(),atlUser);
//        redisUtil.set("USER:" + atlUser.getId(), json);
    }

    @Test
    public void testGet() {
//        String json = redisUtil.get("USER:" + 10000);
        AtlUser atlUser = (AtlUser) redisUtil.getObject("USER:" + 10000);
        System.out.println(atlUser.getUsername());
        System.out.println(atlUser.getLanguage());
    }

    @Test
    public void testDel() {
//        redisService.del("USER:" + 10000);
//        String json = redisService.get("USER:" + 10000);
//        System.out.println(json);
//        String s = redisUtil.get("USER:" + 10000);
        redisUtil.remove("USER:" + 10000);
//        System.out.println(s);
    }

}
