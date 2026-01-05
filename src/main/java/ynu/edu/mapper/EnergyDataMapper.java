package ynu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import ynu.edu.po.EnergyData;

import java.util.List;

/**
 * 能耗数据Mapper
 */
@Mapper
public interface EnergyDataMapper extends BaseMapper<EnergyData> {
    /**
     * 查询设备最近10条数据
     */
    List<EnergyData> selectLatest10ByMeterId(@Param("meterId") Long meterId);

    /**
     * 查询设备最后一条累计用电量
     */
    Double selectLastTotalEnergy(@Param("meterId") Long meterId);
}
