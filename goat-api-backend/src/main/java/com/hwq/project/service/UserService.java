package com.hwq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwq.goatapicommon.model.entity.User;
import com.hwq.project.model.vo.KeyVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author HWQ
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-07-14 17:07:21
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 重置用户AK、SK
     * @param loginUser
     * @return
     */
    User resetUserSecret(User loginUser);
}
