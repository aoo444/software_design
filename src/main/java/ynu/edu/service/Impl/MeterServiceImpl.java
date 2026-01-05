package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ynu.edu.mapper.MeterMapper;
import ynu.edu.po.Meter;
import ynu.edu.service.MeterService;

@Service
@RequiredArgsConstructor
public class MeterServiceImpl extends ServiceImpl<MeterMapper,Meter> implements MeterService {
}
