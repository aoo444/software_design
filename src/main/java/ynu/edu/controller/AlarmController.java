package ynu.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.edu.dto.Result;
import ynu.edu.po.Alarm;
import ynu.edu.service.AlarmService;
import java.util.List;


@Tag(name = "告警接口")
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @Operation(summary = "查询设备告警记录")
    @GetMapping("/list/{meterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Result<List<Alarm>> listByMeterId(@PathVariable Long meterId) {
        List<Alarm> alarmList = alarmService.listByMeterId(meterId);
        return Result.success(alarmList);
    }
}