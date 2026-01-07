package ynu.edu.event;

import org.springframework.context.ApplicationEvent;
import ynu.edu.po.Alarm;

// Pattern: Observer - 告警事件
// 观察者模式中的事件源，用于通知观察者有告警发生
public class AlarmEvent extends ApplicationEvent {
    private final Alarm alarm;

    public AlarmEvent(Object source, Alarm alarm) {
        super(source);
        this.alarm = alarm;
    }

    public Alarm getAlarm() {
        return alarm;
    }
}
