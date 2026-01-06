package ynu.edu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ynu.edu.dto.Result;

// 全局异常处理器
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常：", e);
        return Result.error(e.getMessage());
    }

    // 注意：不要添加@ExceptionHandler(Exception.class)，因为它会捕获所有异常
    // 包括Spring Security的认证和授权异常，导致它们无法被专门的处理器处理
    // Spring Security的认证和授权异常由JwtAuthenticationEntryPoint和JwtAccessDeniedHandler处理
}
