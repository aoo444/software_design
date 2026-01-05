package ynu.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ynu.edu.dto.Result;
import ynu.edu.po.Meter;
import ynu.edu.service.MeterService;

import java.util.List;

/**
 * 电表设备接口
 */
@Tag(name = "电表设备接口")
@RestController
@RequestMapping("/meter")
@RequiredArgsConstructor
public class MeterController {
    private final MeterService meterService;

    @Operation(summary = "查询所有设备")
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Result<List<Meter>> list() {
        return Result.success(meterService.list());
    }

    @Operation(summary = "新增设备（仅管理员）")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> add(@RequestBody Meter meter) {
        return meterService.save(meter) ? Result.success() : Result.error("新增失败");
    }
}