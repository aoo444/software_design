package ynu.edu.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ynu.edu.po.Alarm;

// Pattern: Observer - 告警通知监听器
// 观察者模式中的具体观察者，监听告警事件并执行通知操作
@Slf4j
@Component
public class AlarmNotifyListener {
    @EventListener(AlarmEvent.class)
    public void sendAlarmNotify(AlarmEvent event) {
        Alarm alarm = event.getAlarm();
        log.warn("【告警通知】设备{}触发{}告警！详情：{}，时间：{}",
                alarm.getMeterId(), alarm.getAlarmType(),
                alarm.getAlarmDetail(), alarm.getTriggerTime());
    }
}
