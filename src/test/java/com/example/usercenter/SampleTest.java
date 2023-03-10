package com.example.usercenter;

import com.example.usercenter.mapper.UserMapper;
import com.example.usercenter.model.User;
import org.junit.Assert;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
//        调用mapper   null代表查询所有
        List<User> userList = userMapper.selectList(null);
//        断言    如果不是这样就报错
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
