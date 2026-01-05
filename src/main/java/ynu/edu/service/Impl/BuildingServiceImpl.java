package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ynu.edu.mapper.BuildingMapper;
import ynu.edu.po.Building;
import ynu.edu.service.BuildingService;


@Service
@RequiredArgsConstructor
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper,Building> implements BuildingService {
}
