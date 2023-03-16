package com.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.domain.request.UserLoginRequest;
import com.example.usercenter.model.domain.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 * cotroller层倾向于对请求参数本身的校验，不涉及业务逻辑本身（越少越好）
 * service层是对业务逻辑的校验（有可能被controller之外的类调用）
 * @author chen
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null){
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
//        如果任何一个为空 则不进入数据逻辑层
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null){
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
//        如果任何一个为空 则不进入数据逻辑层
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword,request);
    }
//第二个参数是为了拿到用户登录态
    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){
        if(!isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        如果用户名不为空
        if(StringUtils.isNotBlank(username)){
//            第一个username是数据库列名，第二个username是模糊查找输入的用户名
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> {
           return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
    }
    @PostMapping("/delete")
//    Q?这个注解啥意思
//    A：RequestBody注解的主要作用就是用于接收前端的参数，当我们使用post请求的时候，我们会将参数放在request body中，此时我们就需要在Controller
//    的方法的参数前面加上@RequestBody用来接受到前端传过来的request body中的值，

    public boolean deleteUser(@RequestBody long id,HttpServletRequest request){
        if(!isAdmin(request)) {
            return false;
        }
        if(id <= 0){
            return false;
        }
        return userService.removeById(id);
    }
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
