package ynu.edu.design.strategy;

import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.Random;

// Pattern: Strategy - 实验室功率生成策略
// 策略模式中的具体策略，用于生成实验室的功率数据
@Component
public class LabPowerStrategy implements PowerStrategy {
    private final Random random = new Random();

    @Override
    public Double generatePower(Double ratedPower) {
        LocalTime now = LocalTime.now();
        boolean isDay = now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(22, 0));
        double min, max;
        if (isDay) {
            min = ratedPower * 0.5;
            max = ratedPower * 0.9;
        } else {
            min = 50.0;
            max = 200.0;
        }
        return min + (max - min) * random.nextDouble();
    }
}
