package com.hwq.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:HWQ
 * @DateTime:2023/4/21 14:32
 * @Description: 用户登录实体
 **/
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 7851750037521868036L;

    private String userAccount;

    private String userPassword;
}
