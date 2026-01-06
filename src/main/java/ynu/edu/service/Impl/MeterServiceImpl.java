package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.Meter;
import ynu.edu.service.MeterService;

@Service
public class MeterServiceImpl extends ServiceImpl<MeterMapper,Meter> implements MeterService {
    @Override
    public boolean addMeter(Meter meter) {
        meter.setIs_deleted(0);
        // 输出调试信息
        System.out.println("新建设备信息：" + meter);
        System.out.println("isValid：" + meter.getIsValid());
        System.out.println("status：" + meter.getStatus());
        return super.save(meter);
    }
}
