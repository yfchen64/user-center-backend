package com.example.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author chen
 */
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -543286974259314538L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
