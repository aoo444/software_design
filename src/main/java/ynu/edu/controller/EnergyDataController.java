package ynu.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.edu.dto.EnergyDataDTO;
import ynu.edu.dto.Result;
import ynu.edu.service.EnergyDataService;

import java.util.List;

@Tag(name = "能耗数据接口")
@RestController
@RequestMapping("/energy-data")
@RequiredArgsConstructor
public class EnergyDataController {
    private final EnergyDataService energyDataService;

    @Operation(summary = "查询设备最新能耗数据")
    @GetMapping("/latest/{meterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Result<EnergyDataDTO> getLatestByMeterId(@PathVariable Long meterId) {
        return Result.success(energyDataService.getLatestByMeterId(meterId));
    }

    @Operation(summary = "查询设备最近10条能耗数据")
    @GetMapping("/latest10/{meterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Result<List<EnergyDataDTO>> getLatest10ByMeterId(@PathVariable Long meterId) {
        return Result.success(energyDataService.getLatest10ByMeterId(meterId));
    }
}
