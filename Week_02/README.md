# 学习笔记

## 常用GC命令

``` bash
# 使用并行GC并打印GC详情
java -XX:+PrintGCDetails -XX:+UseParallelGC GCLogAnalysis

# 打印GC详情至日志文件
java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

# 设置小堆内存模拟oom
java -XX:+PrintGCDetails -XX:+UseParallelGC -Xms128m -Xmx128m GCLogAnalysis

# CMS GC
java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

# G1 GC
java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis
```

- Yang GC & Old GC vs Minor GC & Major GC
  - Serial GC -Minor GC: Eden区和S0区的数据回收或转移至S1或Old区
  - Serial GC -Full GC: Old区回收，Eden区和存活区不管
  - Parallel GC -Yang GC: Eden区和S0区的数据回收或转移至S1或Old区
  - Parallel GC -Full GC：Eden区和S0区清除，Old区回收

## JVM 线程模型


## 一个Java对象占用多少内存
G1GC 吞吐量和暂停时间平衡 优先选择 会根据统计数据自动调整启动时间（启发式GC）
并行GC 暂停时间较长，但总体消耗最少
CMSGC 暂停时间较短，但效率较低，暂停时间不可控

