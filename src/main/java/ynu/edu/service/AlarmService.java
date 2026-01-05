package ynu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.edu.po.Alarm;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

import java.util.List;

public interface AlarmService extends IService<Alarm> {
    // 检测能耗数据是否异常，触发告警
    void checkAndTriggerAlarm(EnergyData energyData, Meter meter);
    List<Alarm> listByMeterId(Long meterId);

}
