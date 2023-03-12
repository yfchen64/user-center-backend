package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import com.example.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务实现类
 *
 * @author chen
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Resource
    private UserMapper userMapper;
    @Override
    public long userRegister(String userAccout, String userPassword, String checkPassword) {
//        1.校验
        if(StringUtils.isAnyBlank(userAccout,userPassword,checkPassword)) {
            return -1;
        }
        if(userAccout.length() < 4) {
            return -1;
        }
        if(userPassword.length() < 8 || checkPassword.length() < 8){
            return -1;
        }


//        账户不能有特殊字符
        String validPattern ="[^a-zA-Z0-9]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccout);
        if(matcher.find()){
            return -1;
        }
//        密码和校验密码需要相同
        if(!userPassword.equals(checkPassword)){
            return -1;
        }

        //        账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccout);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0) {
            return -1;
        }



//        加密
        final String SALT = "chen";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

//        插入数据
        User user = new User();
        user.setUserAccount(userAccout);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if(!saveResult) {
            return -1;
        }
        return user.getId();
    }
}




