package ynu.edu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 主键ID
    private String username;            // 用户名
    private String password;            // 加密密码
    private String role;                // 角色：ADMIN/USER
    private Integer status;             // 账号状态：1=启用，0=禁用
    private LocalDateTime createTime;   // 创建时间
}