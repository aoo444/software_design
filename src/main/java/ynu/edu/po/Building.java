package ynu.edu.po;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;


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

    @TableLogic(value = "0", delval = "1") // 0=未删除，1=已删除
    @TableField("is_deleted")
    private Integer isDeleted;
}
