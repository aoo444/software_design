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
        boolean success = meterService.addMeter(meter);
        return success ? Result.success() : Result.error("新增失败");
    }

    @Operation(summary = "删除设备（仅管理员）")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> delete(@PathVariable Long id) {
        boolean success = meterService.removeById(id); // 自动执行UPDATE meter SET deleted=1 WHERE id=?
        return success ? Result.success("删除成功（可恢复）") : Result.error("设备不存在");
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> update(@RequestBody Meter meter) {
        boolean success = meterService.updateMeter(meter);
        return success ? Result.success("更新设备成功") : Result.error("更新设备失败");
    }
}