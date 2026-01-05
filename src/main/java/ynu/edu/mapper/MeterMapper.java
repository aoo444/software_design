package ynu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.edu.po.Meter;

/**
 * 电表设备Mapper
 */
@Mapper
public interface MeterMapper extends BaseMapper<Meter> {
}
