package ynu.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.edu.dto.LoginDTO;
import ynu.edu.dto.Result;
import ynu.edu.service.UserService;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<?> login(@Validated @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
}