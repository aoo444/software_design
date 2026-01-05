package ynu.edu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 电表设备实体类
 */
@Data
@TableName("meter")
public class Meter {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 主键ID
    private String name;                // 设备名称
    private String sn;                  // 设备SN（唯一）
    private Integer status;             // 通讯状态：1=在线，0=离线
    private Double ratedPower;          // 额定功率阈值（W）
    private Long buildingId;            // 所属建筑ID
    private String roomNumber;          // 所属房间号
    private LocalDateTime createTime;   // 创建时间
    private Integer isValid;            // 是否有效：1=有效，0=停用
}