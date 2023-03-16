package com.example.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author chen
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -543286974259314538L;

    private String userAccount;

    private String userPassword;
}
