package ynu.edu.service;

import ynu.edu.po.Alarm;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

/**
 * 告警Service
 */
public interface AlarmService extends BaseService<Alarm> {
    /**
     * 检测能耗数据是否异常，触发告警
     */
    void checkAndTriggerAlarm(EnergyData energyData, Meter meter);
}
