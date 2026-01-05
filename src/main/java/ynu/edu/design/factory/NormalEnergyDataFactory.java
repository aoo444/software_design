package ynu.edu.design.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ynu.edu.design.strategy.PowerStrategy;
import ynu.edu.design.strategy.PowerStrategyContext;
import ynu.edu.mapper.EnergyDataMapper;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;

// 正常能耗数据工厂（工厂模式）
@Component
@RequiredArgsConstructor
public class NormalEnergyDataFactory implements EnergyDataFactory {
    private final PowerStrategyContext powerStrategyContext;
    private final EnergyDataMapper energyDataMapper;
    private final Random random = new Random();

    @Override
    public EnergyData createEnergyData(Meter meter) {
        EnergyData data = new EnergyData();
        data.setMeterId(meter.getId());
        // 生成正态分布随机电压（简化实现）
        double voltage = 220 + (random.nextGaussian() * 5);
        voltage = Math.max(210.0, Math.min(235.0, voltage));
        data.setVoltage(new BigDecimal(voltage).setScale(2, RoundingMode.HALF_UP).doubleValue());

        PowerStrategy strategy = powerStrategyContext.getStrategy(meter);
        double power = strategy.generatePower(meter.getRatedPower());
        data.setRealPower(new BigDecimal(power).setScale(2, RoundingMode.HALF_UP).doubleValue());

        double current = power / voltage;
        data.setCurrent(new BigDecimal(current).setScale(2, RoundingMode.HALF_UP).doubleValue());

        Double lastTotal = energyDataMapper.selectLastTotalEnergy(meter.getId());
        if (lastTotal == null) lastTotal = 0.0;
        double addEnergy = 0.01 + (0.1 - 0.01) * random.nextDouble();
        data.setTotalEnergy(new BigDecimal(lastTotal + addEnergy).setScale(2, RoundingMode.HALF_UP).doubleValue());
        data.setCollectTime(LocalDateTime.now());
        return data;
    }
}