package ynu.edu.design.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

import java.util.Random;

// Pattern: Factory - 能耗数据工厂管理器
// 管理不同的能耗数据工厂，根据条件选择使用正常或异常数据工厂
@Component
@RequiredArgsConstructor
public class EnergyDataFactoryManager {
    private final NormalEnergyDataFactory normalFactory;
    private final AbnormalEnergyDataFactory abnormalFactory;
    private static int normalCount = 0;
    private static final int ABNORMAL_THRESHOLD = new Random().nextInt(31) + 20;

    public EnergyData create(Meter meter) {
        normalCount++;
        if (normalCount >= ABNORMAL_THRESHOLD) {
            normalCount = 0;
            return abnormalFactory.createEnergyData(meter);
        }
        return normalFactory.createEnergyData(meter);
    }
}
