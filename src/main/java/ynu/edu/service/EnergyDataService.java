package ynu.edu.service;

import ynu.edu.dto.EnergyDataDTO;
import ynu.edu.po.EnergyData;

import java.util.List;

/**
 * 能耗数据Service
 */
public interface EnergyDataService extends BaseService<EnergyData> {
    /**
     * 查询设备最新能耗数据
     */
    EnergyDataDTO getLatestByMeterId(Long meterId);

    /**
     * 查询设备最近10条能耗数据
     */
    List<EnergyDataDTO> getLatest10ByMeterId(Long meterId);
}