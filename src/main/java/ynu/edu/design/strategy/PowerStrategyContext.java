package ynu.edu.design.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ynu.edu.po.Meter;
import java.util.Map;

// Pattern: Strategy - 功率策略上下文
// 负责根据不同的设备条件选择合适的功率生成策略
@Component
@RequiredArgsConstructor
public class PowerStrategyContext {
    private final Map<String, PowerStrategy> strategyMap;

    public PowerStrategy getStrategy(Meter meter) {
        String roomNumber = meter.getRoomNumber();
        Long buildingId = meter.getBuildingId();
        if (buildingId == 1) {
            return strategyMap.get("dormitoryPowerStrategy");
        } else if (buildingId == 3 && "306".equals(roomNumber)) {
            return strategyMap.get("labPowerStrategy");
        } else {
            return strategyMap.get("defaultPowerStrategy");
        }
    }
}
