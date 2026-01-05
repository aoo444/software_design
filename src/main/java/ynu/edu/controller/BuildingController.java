package ynu.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ynu.edu.dto.Result;
import ynu.edu.po.Building;
import ynu.edu.service.BuildingService;

import java.util.List;


@Tag(name = "建筑接口")
@RestController
@RequestMapping("/building")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @Operation(summary = "查询所有建筑")
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Result<List<Building>> list() {
        return Result.success(buildingService.list());
    }

    @Operation(summary = "新增建筑（仅管理员）")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> add(@RequestBody Building building) {
        return buildingService.save(building) ? Result.success() : Result.error("新增失败");
    }
}
