package ynu.edu.design.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ynu.edu.po.Meter;
import java.util.Map;

// 功率策略上下文（策略模式）
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
