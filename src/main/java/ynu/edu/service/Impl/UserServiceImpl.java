package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public Result<?> login(LoginDTO loginDTO) {
        try {
            // 1. 认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. 生成JWT Token
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
            String token = jwtUtil.generateToken(userDetails);

            // 3. 返回结果
            return Result.success(token);
        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }
}
