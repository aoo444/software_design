package ynu.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ynu.edu.dto.Result;
import ynu.edu.po.Building;
import ynu.edu.service.BuildingService;

import java.util.List;


@Tag(name = "建筑接口")
@RestController
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private  BuildingService buildingService;

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
        boolean success = buildingService.save(building);
        return success ? Result.success("新增建筑成功") : Result.error("新增建筑失败");
    }

    @Operation(summary = "删除建筑（仅管理员）")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> delete(@PathVariable Long id) {
        boolean success = buildingService.removeById(id); // 自动执行UPDATE meter SET deleted=1 WHERE id=?
        return success ? Result.success("删除成功（可恢复）") : Result.error("设备不存在");
    }
}
