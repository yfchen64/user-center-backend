package com.example.usercenter.service;
import java.util.Date;

import com.example.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author chen
 */

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("testChen");
        user.setUserAccount("123");
        user.setUserPassword("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setAddress("");
        user.setPhone("123");
        user.setEmail("");
        user.setIsValid(0);
        user.setUpdate_time(new Date());
        user.setCreate_time(new Date());
        user.setIsDelete(0);


        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "chen";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
//          密码不能为空
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
//        账号长度不小于4位
        userAccount = "ch";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
//        密码长度不小于8位
        userAccount = "chen";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
//        账号不包含特殊字符
        userAccount = "che n";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
//        两次输入密码相同
        userAccount = "chen";
        userPassword = "12345678";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
//账户不能重复
        userAccount = "123";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "chentest";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

    }
}