package ynu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.edu.dto.LoginDTO;
import ynu.edu.dto.Result;
import ynu.edu.po.User;

public interface UserService extends IService<User> {
    //用户登录
    Result<?> login(LoginDTO loginDTO);
    // 根据用户名查询用户
    User getByUsername(String username);
}
