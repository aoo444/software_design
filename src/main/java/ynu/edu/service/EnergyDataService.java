package ynu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.edu.dto.EnergyDataDTO;
import ynu.edu.po.EnergyData;

import java.util.List;

public interface EnergyDataService extends IService<EnergyData> {
    // 查询设备最新能耗数据
    EnergyDataDTO getLatestByMeterId(Long meterId);

    // 查询设备最近10条能耗数据
    List<EnergyDataDTO> getLatest10ByMeterId(Long meterId);
}