package ynu.edu.design.factory;

import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

// 能耗数据工厂接口（工厂模式）
public interface EnergyDataFactory {
    EnergyData createEnergyData(Meter meter);
}