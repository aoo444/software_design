package ynu.edu.event;

import org.springframework.context.ApplicationEvent;
import ynu.edu.po.Alarm;

// 告警事件（观察者模式）
public class AlarmEvent extends ApplicationEvent {
    private final Alarm alarm;

    public AlarmEvent(Object source, Alarm alarm) {
        super(source);
        this.alarm = alarm;
    }

    // getter
    public Alarm getAlarm() {
        return alarm;
    }
}
