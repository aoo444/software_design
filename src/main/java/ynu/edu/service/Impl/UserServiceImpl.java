package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ynu.edu.dto.LoginDTO;
import ynu.edu.dto.Result;
import ynu.edu.mapper.UserMapper;
import ynu.edu.po.User;
import ynu.edu.security.JwtUtil;
import ynu.edu.service.UserService;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result<?> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
            String token = jwtUtil.generateToken(userDetails);

            return Result.success(token);
        } catch (Exception e) {
            log.error("登录失败", e); // 补充日志，便于排查问题
            return Result.error("用户名或密码错误");
        }
    }

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, username)
        );
    }
}