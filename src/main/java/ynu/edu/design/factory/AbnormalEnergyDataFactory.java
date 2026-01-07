package ynu.edu.design.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ynu.edu.mapper.EnergyDataMapper;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;

// Pattern: Factory - 异常能耗数据工厂
// 工厂模式中的具体工厂，用于创建异常的能耗数据
@Component
@RequiredArgsConstructor
public class AbnormalEnergyDataFactory implements EnergyDataFactory {
    private final EnergyDataMapper energyDataMapper;
    private final Random random = new Random();

    @Override
    public EnergyData createEnergyData(Meter meter) {
        EnergyData data = new EnergyData();
        data.setMeterId(meter.getId());
        int abnormalType = random.nextInt(2);
        if (abnormalType == 0) {
            double power = meter.getRatedPower() * 1.2;
            data.setRealPower(new BigDecimal(power).setScale(2, RoundingMode.HALF_UP).doubleValue());
            double voltage = 210 + (235 - 210) * random.nextDouble();
            data.setVoltage(new BigDecimal(voltage).setScale(2, RoundingMode.HALF_UP).doubleValue());
            data.setCurrent(new BigDecimal(power / voltage).setScale(2, RoundingMode.HALF_UP).doubleValue());
        } else {
            double voltage = random.nextBoolean() ? 180.0 : 260.0;
            data.setVoltage(voltage);
            double power = meter.getRatedPower() * 0.2 + (meter.getRatedPower() * 0.9 - meter.getRatedPower() * 0.2) * random.nextDouble();
            data.setRealPower(new BigDecimal(power).setScale(2, RoundingMode.HALF_UP).doubleValue());
            data.setCurrent(new BigDecimal(power / voltage).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        Double lastTotal = energyDataMapper.selectLastTotalEnergy(meter.getId());
        if (lastTotal == null) lastTotal = 0.0;
        double addEnergy = 0.01 + (0.1 - 0.01) * random.nextDouble();
        data.setTotalEnergy(new BigDecimal(lastTotal + addEnergy).setScale(2, RoundingMode.HALF_UP).doubleValue());
        data.setCollectTime(LocalDateTime.now());
        return data;
    }
}
