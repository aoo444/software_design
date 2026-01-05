package ynu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.edu.po.Alarm;

/**
 * 告警Mapper
 */
@Mapper
public interface AlarmMapper extends BaseMapper<Alarm> {
}