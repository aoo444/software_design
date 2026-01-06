package ynu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.edu.po.Meter;


public interface MeterService extends IService<Meter> {
    boolean addMeter(Meter meter);
}