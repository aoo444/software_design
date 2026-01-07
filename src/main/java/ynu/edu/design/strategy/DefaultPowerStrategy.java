package ynu.edu.design.strategy;

import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.Random;

// Pattern: Strategy - 默认功率生成策略
// 策略模式中的具体策略，用于生成教室/办公室的功率数据
@Component
public class DefaultPowerStrategy implements PowerStrategy {
    private final Random random = new Random();

    @Override
    public Double generatePower(Double ratedPower) {
        LocalTime now = LocalTime.now();
        boolean isDay = now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(22, 0));
        double min, max;
        if (isDay) {
            min = ratedPower * 0.3;
            max = ratedPower * 0.8;
        } else {
            min = 20.0;
            max = 150.0;
        }
        return min + (max - min) * random.nextDouble();
    }
}