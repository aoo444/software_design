package ynu.edu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.edu.mapper.BuildingMapper;
import ynu.edu.po.Building;
import ynu.edu.service.BuildingService;


@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper,Building> implements BuildingService {
    @Override
    public boolean addBuilding(Building building) {
        building.setIsDeleted(0); // 新增时默认未删除
        return super.save(building);
    }
}
