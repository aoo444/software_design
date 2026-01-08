package ynu.edu.design.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ynu.edu.po.Building;
import ynu.edu.po.Meter;
import ynu.edu.service.BuildingService;
import java.util.Map;

// Pattern: Strategy - 功率策略上下文
// 负责根据不同的设备条件选择合适的功率生成策略
@Component
@RequiredArgsConstructor
public class PowerStrategyContext {
    private final Map<String, PowerStrategy> strategyMap;
    private final BuildingService buildingService;

    public PowerStrategy getStrategy(Meter meter) {
        String roomNumber = meter.getRoomNumber();
        Long buildingId = meter.getBuildingId();
        
        // 根据buildingId查询建筑信息
        Building building = buildingService.getById(buildingId);
        
        // 如果建筑不存在，使用默认策略
        if (building == null) {
            return strategyMap.get("defaultPowerStrategy");
        }
        
        String useType = building.getUseType();
        
        // 根据建筑类型选择策略
        if ("宿舍".equals(useType)) {
            return strategyMap.get("dormitoryPowerStrategy");
        } else if ("实验楼/办公楼".equals(useType) && "306".equals(roomNumber)) {
            return strategyMap.get("labPowerStrategy");
        } else {
            return strategyMap.get("defaultPowerStrategy");
        }
    }
}
