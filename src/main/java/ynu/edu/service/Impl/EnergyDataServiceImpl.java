package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ynu.edu.dto.EnergyDataDTO;
import ynu.edu.mapper.EnergyDataMapper;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;
import ynu.edu.service.EnergyDataService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnergyDataServiceImpl extends ServiceImpl<EnergyDataMapper,EnergyData> implements EnergyDataService {
    private final MeterMapper meterMapper;

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
        List<EnergyData> dataList = this.baseMapper.selectLatest10ByMeterId(meterId);
        if (dataList.isEmpty()) return null;
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
