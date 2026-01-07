package ynu.edu.design.strategy;

// Pattern: Strategy - 功率生成策略接口
// 定义了功率生成的抽象策略，具体实现由不同的策略类提供
public interface PowerStrategy {
    // 生成功率值，参数为设备额定功率
    Double generatePower(Double ratedPower);
}
