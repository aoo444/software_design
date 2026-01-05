package ynu.edu.service;

import ynu.edu.dto.LoginDTO;
import ynu.edu.dto.Result;
import ynu.edu.po.User;

/**
 * 用户Service
 */
public interface UserService extends BaseService<User> {
    /**
     * 用户登录
     */
    Result<?> login(LoginDTO loginDTO);

    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);
}
