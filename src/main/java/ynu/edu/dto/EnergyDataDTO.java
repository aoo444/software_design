package ynu.edu.dto;

import lombok.Data;
import java.time.LocalDateTime;

// 能耗数据响应DTO
@Data
public class EnergyDataDTO {
    private Long id;                    // 主键ID
    private Long meterId;               // 设备ID
    private String meterName;           // 设备名称（冗余）
    private Double voltage;             // 电压（V）
    private Double current;             // 电流（A）
    private Double realPower;           // 实时功率（W）
    private Double totalEnergy;         // 累计用电量（kWh）
    private LocalDateTime collectTime;  // 采集时间
}
