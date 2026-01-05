package ynu.edu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 告警实体类
 */
@Data
@TableName("alarm")
public class Alarm {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 主键ID
    private Long meterId;               // 所属设备ID
    private String alarmType;           // 告警类型：OVERLOAD/VOLTAGE_ABNORMAL
    private Double alarmValue;          // 告警数值
    private String alarmDetail;         // 告警详情
    private LocalDateTime triggerTime;  // 触发时间
}