package ynu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.edu.po.Building;

public interface BuildingService extends IService<Building> {
    boolean addBuilding(Building building);
    boolean updateBuilding(Building building);
}
