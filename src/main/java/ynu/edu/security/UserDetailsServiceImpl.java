package ynu.edu.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ynu.edu.po.User;
import ynu.edu.service.UserService;

import java.util.Collections;

/**
 * 用户详情服务实现（适配Spring Boot 3）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        User user = userService.getByUsername(username);
        if (user == null) {
            log.error("用户名{}不存在", username);
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 检查账号状态
        if (user.getStatus() != 1) {
            log.error("用户名{}已禁用", username);
            throw new UsernameNotFoundException("账号已禁用");
        }

        // 构建UserDetails（适配Spring Boot 3）
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}