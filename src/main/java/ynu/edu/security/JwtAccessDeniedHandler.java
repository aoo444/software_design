package ynu.edu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ynu.edu.dto.Result;

import java.io.IOException;
import java.io.PrintWriter;

// JWT授权失败处理器
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        log.error("授权失败：{}", accessDeniedException.getMessage());
        // 设置响应格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 构建失败响应
        Result<?> result = Result.error(HttpServletResponse.SC_FORBIDDEN, "授权失败：" + accessDeniedException.getMessage());

        // 写入响应
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}