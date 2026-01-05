package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ynu.edu.design.factory.EnergyDataFactoryManager;
import ynu.edu.mapper.EnergyDataMapper;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;
import ynu.edu.service.AlarmService;

import java.util.List;

// 数据模拟Service（定时任务）
@Slf4j
@Service
@RequiredArgsConstructor
public class SimulatorServiceImpl {
    private final MeterMapper meterMapper;
    private final EnergyDataMapper energyDataMapper;
    private final EnergyDataFactoryManager dataFactory;
    private final AlarmService alarmService;

    // 定时采集能耗数据（每5秒）
    @Scheduled(fixedRateString = "${scheduled.data-collect-interval}")
    public void collectEnergyData() {
        log.info("开始采集能耗数据...");

        // 1. 查询所有有效设备（isValid=1，status=1）
        List<Meter> validMeters = meterMapper.selectList(
                new LambdaQueryWrapper<Meter>()
                        .eq(Meter::getIsValid, 1)
                        .eq(Meter::getStatus, 1)
        );
        if (validMeters.isEmpty()) {
            log.warn("无有效设备，跳过本次采集");
            return;
        }

        // 2. 为每个设备生成并保存数据
        for (Meter meter : validMeters) {
            // 生成能耗数据（工厂模式）
            EnergyData data = dataFactory.create(meter);
            // 保存数据
            energyDataMapper.insert(data);
            // 检测告警
            alarmService.checkAndTriggerAlarm(data, meter);
        }

        log.info("采集完成，共生成{}条能耗数据", validMeters.size());
    }
}
