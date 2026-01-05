package ynu.edu.design.strategy;

import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.Random;

// 宿舍功率生成策略
@Component
public class DormitoryPowerStrategy implements PowerStrategy {
    private final Random random = new Random();

    @Override
    public Double generatePower(Double ratedPower) {
        LocalTime now = LocalTime.now();
        boolean isDay = now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(22, 0));
        double min, max;
        if (isDay) {
            min = ratedPower * 0.2;
            max = ratedPower * 0.9;
        } else {
            min = 10.0;
            max = 100.0;
        }
        return min + (max - min) * random.nextDouble();
    }
}
