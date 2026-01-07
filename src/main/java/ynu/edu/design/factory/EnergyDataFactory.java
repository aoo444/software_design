package ynu.edu.design.factory;

import ynu.edu.po.EnergyData;
import ynu.edu.po.Meter;

// Pattern: Factory - 能耗数据工厂接口
// 定义了创建能耗数据的抽象工厂，具体实现由不同的工厂类提供
public interface EnergyDataFactory {
    // 创建能耗数据，参数为设备信息
    EnergyData createEnergyData(Meter meter);
}