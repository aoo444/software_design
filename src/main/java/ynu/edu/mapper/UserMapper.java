package ynu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import ynu.edu.po.User;


public interface UserMapper extends BaseMapper<User> {
    // 根据用户名查询用户
    User selectByUsername(@Param("username") String username);
}
