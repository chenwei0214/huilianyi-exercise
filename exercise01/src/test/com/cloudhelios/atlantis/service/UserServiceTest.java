package com.cloudhelios.atlantis.service;

import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.dto.AtlUserInDTO;
import com.cloudhelios.atlantis.util.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * author: chenwei
 * createDate: 18-8-28 下午5:47
 * description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


    @Autowired
    private AtlUserService atlUserService;

    @Test
    public void testCreateIndex() throws IOException {
        atlUserService.createIndex();
    }
    
    @Test
    public void testSearchByLucene(){
        AtlUserInDTO atlUser=new AtlUserInDTO();
        atlUser.setKeyword("2341");
        Page<AtlUser> search = atlUserService.searchByLucene(atlUser);
    }

}

