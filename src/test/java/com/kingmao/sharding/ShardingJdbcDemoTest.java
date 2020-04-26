package com.kingmao.sharding;

import com.kingmao.sharding.dao.UserMapper;
import com.kingmao.sharding.entity.User;
import com.kingmao.sharding.entity.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * @author qinxuan
 * @date 2020/4/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcDemoTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        for (int i = 1; i <100; i ++ ){
            User u = new User();
            u.setUserId(i);
            u.setAge(i);
            u.setName(UUID.randomUUID().toString().substring(0,6));
            userMapper.insert(u);
        }

    }

    @Test
    public void test (){
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("age asc");
        userExample.setLimitStart(0);
        userExample.setLimitEnd(4);
        List<User> users = userMapper.selectByExample(userExample);
        if (users == null){
            System.out.println("没有查到数据");
            return;
        }
        users.forEach(item->{
            System.out.println(item.toString());
        });
    }
}
