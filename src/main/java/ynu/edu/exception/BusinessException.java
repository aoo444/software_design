package ynu.edu.exception;

import lombok.Getter;

// 自定义业务异常
@Getter
public class BusinessException extends RuntimeException {
    private final String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
