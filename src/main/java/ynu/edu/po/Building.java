package ynu.edu.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 建筑实体类
 */
@Data
@TableName("building")
public class Building {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 主键ID
    private String name;                // 建筑名称
    private String locationCode;        // 位置编码
    private Integer floorCount;         // 楼层数
    private String useType;             // 建筑用途
    private LocalDateTime createTime;   // 创建时间
}
