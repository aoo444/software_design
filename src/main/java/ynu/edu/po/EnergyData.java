package ynu.edu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@TableName("energy_data")
public class EnergyData {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 主键ID
    private Long meterId;               // 所属设备ID
    private Double voltage;             // 电压（V）
    private Double current;             // 电流（A）
    private Double realPower;           // 实时功率（W）
    private Double totalEnergy;         // 累计用电量（kWh）
    private LocalDateTime collectTime;  // 采集时间
}
