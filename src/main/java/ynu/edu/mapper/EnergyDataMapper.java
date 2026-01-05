package ynu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import ynu.edu.po.EnergyData;

import java.util.List;


public interface EnergyDataMapper extends BaseMapper<EnergyData> {
    // 查询设备最近10条数据
    List<EnergyData> selectLatest10ByMeterId(@Param("meterId") Long meterId);

    // 查询设备最后一条累计用电量
    Double selectLastTotalEnergy(@Param("meterId") Long meterId);
}
