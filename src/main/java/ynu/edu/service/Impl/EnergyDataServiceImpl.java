package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.edu.dto.EnergyDataDTO;
import ynu.edu.mapper.EnergyDataMapper;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;
import ynu.edu.service.EnergyDataService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnergyDataServiceImpl extends ServiceImpl<EnergyDataMapper,EnergyData> implements EnergyDataService {
    @Autowired
    private  MeterMapper meterMapper;

    @Override
    public EnergyDataDTO getLatestByMeterId(Long meterId) {
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyData::getMeterId, meterId)
                .orderByDesc(EnergyData::getCollectTime)
                .last("LIMIT 1");
        EnergyData energyData = this.baseMapper.selectOne(wrapper);
        if (energyData == null) return null;
        EnergyDataDTO dto = new EnergyDataDTO();
        BeanUtils.copyProperties(energyData, dto);
        Meter meter = meterMapper.selectById(meterId);
        if (meter != null) {
            dto.setMeterName(meter.getName());
        }
        return dto;
    }

    @Override
    public List<EnergyDataDTO> getLatest10ByMeterId(Long meterId) {
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyData::getMeterId, meterId) // 匹配电表ID
                .orderByDesc(EnergyData::getCollectTime) // 按采集时间降序
                .last("LIMIT 10"); // 取最新10条
        List<EnergyData> dataList = this.baseMapper.selectList(wrapper);

        if (dataList.isEmpty()) {
            return Collections.emptyList(); // 返回空列表（推荐），而非null
        }

        Meter meter = meterMapper.selectById(meterId);
        String meterName = meter != null ? meter.getName() : "";
        return dataList.stream().map(data -> {
            EnergyDataDTO dto = new EnergyDataDTO();
            BeanUtils.copyProperties(data, dto);
            dto.setMeterName(meterName);
            return dto;
        }).collect(Collectors.toList());
    }
}
