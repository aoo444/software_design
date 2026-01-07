package ynu.edu.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ynu.edu.mapper.AlarmMapper;
import ynu.edu.po.Alarm;

// Pattern: Observer - 告警日志监听器
// 观察者模式中的具体观察者，监听告警事件并执行日志记录操作
@Slf4j
@Component
public class AlarmLogListener {
    @Autowired
    private AlarmMapper alarmMapper;

    @EventListener(AlarmEvent.class)
    public void saveAlarmLog(AlarmEvent event) {
        Alarm alarm = event.getAlarm();
        alarmMapper.insert(alarm);
        log.info("【告警入库】设备{}发生{}告警，数值：{}",
                alarm.getMeterId(), alarm.getAlarmType(), alarm.getAlarmValue());
    }
}
