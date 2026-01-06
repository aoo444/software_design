package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.Meter;
import ynu.edu.service.MeterService;

@Service
public class MeterServiceImpl extends ServiceImpl<MeterMapper,Meter> implements MeterService {
    // 在保存前给软删除字段赋值
    @Override
    public boolean addMeter(Meter meter) {
        meter.setIs_deleted(0);
        return super.save(meter);
    }
}
