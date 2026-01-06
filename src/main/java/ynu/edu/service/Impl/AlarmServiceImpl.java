package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ynu.edu.event.AlarmEvent;
import ynu.edu.mapper.AlarmMapper;
import ynu.edu.po.Alarm;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;
import ynu.edu.service.AlarmService;

import java.time.LocalDateTime;
import java.util.List;

// 告警Service实现（观察者模式）
@Slf4j
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements AlarmService {
    @Autowired
    private  ApplicationEventPublisher eventPublisher;

    @Override
    public void checkAndTriggerAlarm(EnergyData energyData, Meter meter) {
        Alarm alarm = null;
        double ratedPower = meter.getRatedPower();
        double voltage = energyData.getVoltage();
        double realPower = energyData.getRealPower();

        // 1. 功率超限检测（>额定功率）
        if (realPower > ratedPower) {
            alarm = new Alarm();
            alarm.setMeterId(meter.getId());
            alarm.setAlarmType("OVERLOAD");
            alarm.setAlarmValue(realPower);
            alarm.setAlarmDetail(String.format("功率超限：%.2fW，额定阈值：%.2fW", realPower, ratedPower));
            alarm.setTriggerTime(LocalDateTime.now());
        }
        // 2. 电压异常检测（偏离220V±10% → 198-242V）
        else if (voltage < 198 || voltage > 242) {
            alarm = new Alarm();
            alarm.setMeterId(meter.getId());
            alarm.setAlarmType("VOLTAGE_ABNORMAL");
            alarm.setAlarmValue(voltage);
            alarm.setAlarmDetail(String.format("电压异常：%.2fV，标准范围：198-242V", voltage));
            alarm.setTriggerTime(LocalDateTime.now());
        }

        // 发布告警事件
        if (alarm != null) {
            eventPublisher.publishEvent(new AlarmEvent(this, alarm));
        }
    }

    public List<Alarm> listByMeterId(Long meterId) {
        return this.lambdaQuery()
                .eq(Alarm::getMeterId, meterId)
                .orderByDesc(Alarm::getTriggerTime)
                .list();
    }
}
