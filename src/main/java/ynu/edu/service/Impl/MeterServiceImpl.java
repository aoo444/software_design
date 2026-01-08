package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.edu.exception.BusinessException;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.Meter;
import ynu.edu.service.MeterService;

@Service
public class MeterServiceImpl extends ServiceImpl<MeterMapper,Meter> implements MeterService {
    @Override
    public boolean addMeter(Meter meter) {
        // 检查设备编号是否重复
        Meter existingMeter = this.getOne(Wrappers.<Meter>lambdaQuery()
                .eq(Meter::getSn, meter.getSn())
                .eq(Meter::getIs_deleted, 0));
        
        if (existingMeter != null) {
            throw new BusinessException("设备编号已存在");
        }
        
        meter.setIs_deleted(0);
        // 输出调试信息
        System.out.println("新建设备信息：" + meter);
        System.out.println("isValid：" + meter.getIsValid());
        System.out.println("status：" + meter.getStatus());
        return super.save(meter);
    }

    @Override
    public boolean updateMeter(Meter meter) {
        if (meter.getId() == null) {
            return false; // id为空直接返回失败
        }
        return this.updateById(meter);
    }
}
